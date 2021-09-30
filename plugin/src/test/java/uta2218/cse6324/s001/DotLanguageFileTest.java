package uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;
import java.util.HashMap;
import java.util.Set;
import java.io.File;

public class DotLanguageFileTest extends TestCase {
   protected Graph<HashMap<String, String>, HashMapEdge> emptyGraphExpected;
   private File testResourcePath;
   protected Graph<HashMap<String, String>, HashMapEdge> graphA;
   protected Graph<HashMap<String, String>, HashMapEdge> graphB;
   protected Graph<HashMap<String, String>, HashMapEdge> graphC;
   protected Graph<HashMap<String, String>, HashMapEdge> graphD;

   private File emptyPath;
   private File oneInvestor;
   private File onePurchase;
   private File oneStock;
   private File varietyOfOrders;

   // assigning the values
   protected void setUp(){
      emptyGraphExpected = DotLanguageFile.createEmptyGraph();
      testResourcePath = new File("src/test/resources");
      // Setup empty graph file
      emptyPath = new File(testResourcePath.getPath() + "/empty-graph.dot");

      // Setup investor graph for testing
      graphA = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> nodeA1 = new HashMap<String, String>();
      nodeA1.put("TYPE", "Investor");
      nodeA1.put("Name", "Dinesh");
      graphA.addVertex(nodeA1);
      oneInvestor = new File(testResourcePath.getPath() + "/one-investor.dot");

      // Setup stock graph for testing
      graphB = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> nodeA2 = new HashMap<String, String>();
      nodeA2.put("TYPE", "Stock");
      nodeA2.put("Symbol", "AAPL");
      graphB.addVertex(nodeA2);
      oneStock = new File(testResourcePath.getPath() + "/one-stock.dot");

      // setup single purchase graph
      graphC = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> edgeA1A2_1 = new HashMap<String, String>();
      edgeA1A2_1.put("TYPE", "BUYLIMITORDER");
      graphC.addVertex(nodeA1);
      graphC.addVertex(nodeA2);
      graphC.addVertex(edgeA1A2_1);
      onePurchase = new File(testResourcePath.getPath() + "/one-purchase.dot");

      // setup two purchase graph
      graphD = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> edgeA1A2_2 = new HashMap<String, String>();
      edgeA1A2_1.put("TYPE", "MARKETLIMITORDER");
      graphD.addVertex(nodeA1);
      graphD.addVertex(nodeA2);
      graphD.addVertex(edgeA1A2_1);
      graphD.addVertex(edgeA1A2_2);
      varietyOfOrders = new File(testResourcePath.getPath() + "/variety-of-orders.dot");
   }

   // test empty graph is actually empty
   public void testEmpty(){
      Set<HashMap<String, String>> vertices = emptyGraphExpected.vertexSet();
      Set<HashMapEdge> edges = emptyGraphExpected.edgeSet();
      assertTrue(vertices.isEmpty());
      assertTrue(edges.isEmpty());
   }

   // test we can successfully read in dotfile content
   public void testRead() throws Exception {
     String expectedEmpty = "digraph G {\n}\n";
     DotLanguageFile file = new DotLanguageFile(emptyPath);
     assertEquals(expectedEmpty, file.read());
   }

   // test can read a graph with one node
   public void testSimpleNonempty() throws Exception {
      DotLanguageFile fileA = new DotLanguageFile(oneInvestor);
      DotLanguageFile fileB = new DotLanguageFile(oneStock);
      assertTrue(GraphUtility.equals(
            graphA,
            fileA.parse()
      ));
      assertTrue(GraphUtility.equals(
            graphB,
            fileB.parse()
      ));
   }

   // test can read a graph with one node
   public void testComplexNonempty() throws Exception {
      DotLanguageFile fileA = new DotLanguageFile(onePurchase);
      DotLanguageFile fileB = new DotLanguageFile(varietyOfOrders);
      assertTrue(GraphUtility.equals(
            graphC,
            fileA.parse()
      ));
      assertTrue(GraphUtility.equals(
            graphD,
            fileB.parse()
      ));
   }
}
