package io.github.v3nd3774.uta2218.cse6324.s001;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import java.util.HashMap;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
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
                    return result.single().get( 0 ).asString();
                }
            });
            out = result;
        }
        catch(Exception e) {
          // just don't do anything
        }
        return out;
    }

    public Result runQueryResult(String query) {
        System.out.println("1");
        Result out = null;
        try ( Session session = driver.session() )
        {
        System.out.println("1");
            Result result = session.writeTransaction( new TransactionWork<Result>()
            {
                @Override
                public Result execute( Transaction tx )
                {
                    Result result = tx.run(query);
                    return result;
                }
            });
            out = result;
        }
        catch(Exception e) {
          // just don't do anything
        }
        System.out.println("1");
        return out;
    }

    public Graph<HashMap<String, String>, HashMapEdge> readState() {
        Graph<HashMap<String, String>, HashMapEdge> out = DotLanguageFile.createEmptyGraph();
        return out;
    }
}
