package io.github.v3nd3774.uta2218.cse6324.s001;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

// from https://neo4j.com/developer/java/
public class Neo4JUtility implements AutoCloseable
{
    private final Driver driver;

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
        try ( Session session = driver.session() )
        {
            String result = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "SHOW DATABASE $target",
                            parameters( "target", "neo4j" ) );
                    System.out.println(result.toString());
                    return result.single().get( 0 ).asString();
                }
            });
            flag = true;
        }
        catch(Exception e) {
          // just don't do anything
        }
        return flag;
    }
}
