package io.github.v3nd3774.uta2218.cse6324.s001;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

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

    public String objToString(HashMap<String, String> eSource, String start, String end){
      String sourceNode = start;
      Integer limit = eSource.keySet().size();
      if (eSource.containsKey("TYPE")){
        sourceNode = sourceNode + ":" + eSource.get("TYPE");
        limit = limit - 1;
      }
      sourceNode = sourceNode + " ";
      if (eSource.keySet().size() > 1){
        Integer counter = 0;
        sourceNode = sourceNode + "{";
        for (Entry entryPair : eSource.entrySet()) {
          String k = (String)entryPair.getKey();
          if(!k.equals("TYPE")){
            String v = (String)entryPair.getValue();
            counter = counter + 1;
            sourceNode = sourceNode + " " + k + ": " + "'" + v + "'" ;
            if(counter < limit){
              //trailing comma
              sourceNode = sourceNode + ", ";
            }
          }
        }
        sourceNode = sourceNode + "}";
      }
      sourceNode = sourceNode + end;
      return sourceNode;
    }

    public String nodeToString(HashMap<String, String> eSource, String type){
      String start = "(" + type;
      return objToString(eSource, start, ")");
    }
    public String srcNodeToString(HashMap<String, String> eSource){
      return nodeToString(eSource, "source");
    }
    public String tarNodeToString(HashMap<String, String> eSource){
      return nodeToString(eSource, "target");
    }

    public String edgeToString(HashMap<String, String> eSource){
      return objToString(eSource, "[edge", "]");
    }

    public void conformGraph(Graph<HashMap<String, String>, HashMapEdge> target) {
      Set<HashMap<String, String>> targetVertices = target.vertexSet();
      Set<HashMapEdge> targetEdges = target.edgeSet();

      Graph<HashMap<String, String>, HashMapEdge> current = this.readState();
      Set<HashMap<String, String>> currentVertices = current.vertexSet();
      Set<HashMapEdge> currentEdges = current.edgeSet();

      Set<HashMap<String, String>> desiredNodes = new HashSet<HashMap<String, String>>();
      Set<HashMap<String, String>> undesirableNodes = new HashSet<HashMap<String, String>>();
      Set<HashMap<String, String>> nodesToBeCreated = new HashSet<HashMap<String, String>>();

      for(HashMap<String, String> vertex: currentVertices) {
        Boolean isDesired = false;
        for(HashMap<String, String> targetVertex: targetVertices) {
          if(targetVertex.equals(vertex)){
            isDesired = true;
          }
        }
        if(isDesired){
          desiredNodes.add(vertex);
        } else {
          undesirableNodes.add(vertex);
        }
      }
      for(HashMap<String, String> targetVertex: targetVertices) {
        Boolean isPresent = false;
        for(HashMap<String, String> vertex: desiredNodes) {
          if(vertex.equals(targetVertex)){
            isPresent = true;
          }
        }
        if(!isPresent){
          nodesToBeCreated.add(targetVertex);
        }
      }

      Set<HashMapEdge> desiredEdges = new HashSet<HashMapEdge>();
      Set<HashMapEdge> undesirableEdges = new HashSet<HashMapEdge>();
      Set<HashMapEdge> edgesToBeCreated = new HashSet<HashMapEdge>();

      for(HashMapEdge edge : currentEdges) {
        HashMap<String, String> currentLabel = edge.getLabel();
        Boolean isDesired = false;

        for(HashMapEdge targetEdge: targetEdges) {
          HashMap<String, String> targetLabel = targetEdge.getLabel();
          if(currentLabel.equals(targetLabel)){
            isDesired = true;
          }
        }
        if(isDesired){
          desiredEdges.add(edge);
        } else {
          undesirableEdges.add(edge);
        }
      }

      for(HashMapEdge targetEdge : targetEdges) {
        HashMap<String, String> targetLabel = targetEdge.getLabel();
        Boolean isPresent = false;

        for(HashMapEdge edge: desiredEdges) {
          HashMap<String, String> edgeLabel = edge.getLabel();
          if(edgeLabel.equals(targetLabel)){
            isPresent = true;
          }
        }
        if(!isPresent){
          edgesToBeCreated.add(targetEdge);
        }
      }

      // Delete unwanted edges first
      for (HashMapEdge edge : undesirableEdges) {
        HashMap<String, String> edgeLabel = edge.getLabel();
        String edgeString = this.edgeToString(edgeLabel);
        HashMap<String, String> eSource = edge.getSource();
        String sourceNode = this.srcNodeToString(eSource);
        HashMap<String, String> eTarget = edge.getTarget();
        String targetNode = this.tarNodeToString(eTarget);
        String query = "match " + sourceNode + "-" + edgeString + "-" + targetNode + " with edge, properties(edge) as m detach delete edge return m";
        this.runQuery(query);
      }

      // Delete unwanted nodes next
      for (HashMap<String, String> node : undesirableNodes) {
        String sourceNode = this.srcNodeToString(node);
        String query = "match " + sourceNode + " with source, properties(source) as m detach delete source return m";
        this.runQuery(query);
      }

      // Add desired nodes
      for (HashMap<String, String> node : nodesToBeCreated) {
        String sourceNode = this.srcNodeToString(node);
        String query = "merge " + sourceNode + " return source";
        this.runQuery(query);
      }

      // Add desired edges
      for (HashMapEdge edge : edgesToBeCreated) {
        HashMap<String, String> edgeLabel = edge.getLabel();
        String edgeString = this.edgeToString(edgeLabel);
        HashMap<String, String> eSource = edge.getSource();
        String sourceNode = this.srcNodeToString(eSource);
        HashMap<String, String> eTarget = edge.getTarget();
        String targetNode = this.tarNodeToString(eTarget);
        String query = "match " + sourceNode + ", " + targetNode + " merge (source)-" + edgeString + "-(target) return source, edge, target";
        this.runQuery(query);
      }
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
          Map<String, Object> relData = rel.asMap();
          for(Map.Entry<String, Object> e : relData.entrySet()) {
            String key = e.getKey();
            String value = (String)e.getValue();
            relMap.put(key, value);
          }
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
