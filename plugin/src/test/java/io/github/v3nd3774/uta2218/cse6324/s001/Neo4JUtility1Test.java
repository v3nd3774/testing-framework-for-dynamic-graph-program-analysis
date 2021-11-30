package io.github.v3nd3774.uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;
import java.util.stream.Stream;

import net.jcip.annotations.NotThreadSafe;

import java.util.List;
import org.junit.experimental.categories.Category;
import java.util.HashMap;
import java.util.Map;
import org.neo4j.driver.Result;
import org.neo4j.driver.summary.ResultSummary;

@NotThreadSafe
public class Neo4JUtility1Test extends TestCase {
   // assumes an empty test instance
   // assuming test instance xists wit hthis settings
   protected String localTestConnectString = "bolt://localhost:7687";
   protected String localTestConnectUser = "neo4j";
   protected String localTestConnectPassword = "test";
   protected String localTestConnectPasswordWrong = "test1";
   protected Neo4JUtility util;
   protected Neo4JUtility badConnectionUtil;

   // assigning the values
   protected void setUp(){
      util = new Neo4JUtility(localTestConnectString, localTestConnectUser, localTestConnectPassword);
      badConnectionUtil = new Neo4JUtility(localTestConnectString, localTestConnectUser, localTestConnectPasswordWrong);
   }

   // test ability to read empty state
   public void testReadEmptyState() throws Exception {
      List<Map<String, Object>> r = util.runQueryResult("MATCH (n) DETACH DELETE n");
      assertTrue(GraphUtility.equals(DotLanguageFile.createEmptyGraph(), util.readState()));
   }
}
