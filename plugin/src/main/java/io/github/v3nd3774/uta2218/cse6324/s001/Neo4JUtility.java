package io.github.v3nd3774.uta2218.cse6324.s001;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.jgrapht.Graph;


// from https://neo4j.com/developer/java/
public class Neo4JUtility implements AutoCloseable
{
    private final Driver driver;
    public final String emptyResultString = "NORESULTS";
    public Neo4JUtility( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public Boolean isConnected()
    {
        Boolean flag = false;
        String heartbeat = this.runQuery("SHOW DATABASE neo4j;");
        if(!heartbeat.equals(this.emptyResultString)) {
          flag = true;
        }
        return flag;
    }

    public String runQuery(String query) {
        String out = this.emptyResultString;
        try ( Session session = driver.session() )
        {
            String result = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run(query);
                    return result.peek().fields().get(0).toString();
                }
            });
            out = result;
        }
        catch(Exception e) {
          // just don't do anything
        }
        return out;
    }

    public List<Map<String, Object>> runQueryResult(String query) {
        List<Map<String, Object>> out = null;
        try ( Session session = driver.session() )
        {
            List<Map<String, Object>> result = session.writeTransaction( new TransactionWork<List<Map<String, Object>>>()
            {
                @Override
                public List<Map<String, Object>> execute( Transaction tx )
                {
                    Result result = tx.run(query);
                    List<Map<String, Object>> ou = result.list((Record r) -> r.asMap());
                    return ou;
                }
            });
            out = new ArrayList(result);
        }
        catch(Exception e) {
          // just don't do anything
        }
        return out;
    }

    public void conformGraph(Graph<HashMap<String, String>, HashMapEdge> target) {
      System.out.println("do stuff");
    }

    public Graph<HashMap<String, String>, HashMapEdge> readState() {
        List<Map<String, Object>> r = this.runQueryResult("match (n)-[r]->(m) return n, r, m");
        
        // modified from https://neo4j.com/docs/java-reference/current/java-embedded/cypher-java/
        Graph<HashMap<String, String>, HashMapEdge> out = DotLanguageFile.createEmptyGraph();

        for (Map<String, Object> entry : r)  {
          System.out.println("ok");
          Node src = (Node)entry.get("n");
          String srcLabel = src.labels().iterator().next();
          Map<String, Object> srcData = src.asMap();
          Map<String, String> srcMap = new HashMap<String, String>();

          for(Map.Entry<String, Object> e : srcData.entrySet()) {
            String key = e.getKey();
            String value = (String)e.getValue();
            srcMap.put(key, value);
          }
          srcMap.put("TYPE", srcLabel);

          Relationship rel = (Relationship)entry.get("r");
          String relType = rel.type();
          HashMap<String, String> relMap = new HashMap<String, String>();
          relMap.put("TYPE", relType);
          HashMapEdge edge = new HashMapEdge(relMap);

          Node dest = (Node)entry.get("m");
          String destLabel = dest.labels().iterator().next();
          Map<String, Object> destData = dest.asMap();
          Map<String, String> destMap = new HashMap<String, String>();
          for(Map.Entry<String, Object> e : destData.entrySet()) {
            String key = e.getKey();
            String value = (String)e.getValue();
            destMap.put(key, value);
          }
          destMap.put("TYPE", destLabel);
          out.addVertex((HashMap<String, String>)srcMap);
          out.addVertex((HashMap<String, String>)destMap);
          out.addEdge((HashMap<String, String>)srcMap, (HashMap<String, String>)destMap, edge);
        };
        return out;
    }
}
