package io.github.v3nd3774.uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;
import java.util.stream.Stream;

import net.jcip.annotations.NotThreadSafe;

import java.util.List;
import org.junit.experimental.categories.Category;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.neo4j.driver.Result;
import org.neo4j.driver.summary.ResultSummary;

@NotThreadSafe
public class Neo4JUtility3Test extends TestCase {
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

   // read nonempty state
   public void testConform() throws Exception {
      // Delete all data in graph
      util.runQuery("match (n)-[r]->() delete n, r");
      util.runQuery("match (n) delete n");
      // Setup test
      util.runQuery("CREATE a = (andy:Person {name:'Andy'}) return a");
      util.runQuery("CREATE m = (michael:Person {name:'Michael'})-[:WORKS_AT {years: '2'}]->(neo:Company {name: 'Neo4J'}) return m");
      Graph<HashMap<String, String>, HashMapEdge> startingGraph = util.readState();

      //Setup target graph in-memory
      Graph<HashMap<String, String>, HashMapEdge> targetGraph = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> company = new HashMap<String, String>();
      company.put("TYPE", "Company");
      company.put("name", "Neo4J");
      HashMap<String, String> person = new HashMap<String, String>();
      person.put("TYPE", "Person");
      person.put("name", "Andy");
      HashMap<String, String> edge = new HashMap<String, String>();
      edge.put("TYPE", "WORKS_AT");
      edge.put("years", "1");
      HashMapEdge edgeObj = new HashMapEdge(edge);
      targetGraph.addVertex(company);
      targetGraph.addVertex(person);
      targetGraph.addEdge(person, company, edgeObj);

      //Conform
      //it should remove michael
      util.conformGraph(targetGraph);

      // re-read state
      Graph<HashMap<String, String>, HashMapEdge> terminatingGraph = util.readState();

      //check state is different than it started
      assertTrue(!GraphUtility.equals(startingGraph, terminatingGraph));
      //check state is same as target
      assertTrue(GraphUtility.equals(targetGraph, terminatingGraph));
   }
}
