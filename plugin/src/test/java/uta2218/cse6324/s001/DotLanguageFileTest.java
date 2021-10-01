package uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.io.File;

public class DotLanguageFileTest extends TestCase {
   protected Graph<HashMap<String, String>, HashMapEdge> emptyGraphExpected;
   private File testResourcePath;
   protected Graph<HashMap<String, String>, HashMapEdge> graphA;
   protected Graph<HashMap<String, String>, HashMapEdge> graphB;
   protected Graph<HashMap<String, String>, HashMapEdge> graphC;
   protected Graph<HashMap<String, String>, HashMapEdge> graphD;
   private Lock sequential = new ReentrantLock();

   private File emptyPath;
   private File oneInvestor;
   private File onePurchase;
   private File oneStock;
   private File varietyOfOrders;


   // assigning the values
   protected void setUp(){
      sequential.lock();
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
      HashMapEdge edgeA1A2_1H = new HashMapEdge(edgeA1A2_1);
      graphC.addVertex(nodeA1);
      graphC.addVertex(nodeA2);
      graphC.addEdge(nodeA1, nodeA2, edgeA1A2_1H);
      onePurchase = new File(testResourcePath.getPath() + "/one-purchase.dot");

      // setup two purchase graph
      graphD = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> edgeA1A2_2 = new HashMap<String, String>();
      edgeA1A2_2.put("TYPE", "MARKETLIMITORDER");
      HashMapEdge edgeA1A2_2H = new HashMapEdge(edgeA1A2_2);
      graphD.addVertex(nodeA1);
      graphD.addVertex(nodeA2);
      graphD.addEdge(nodeA1, nodeA2, edgeA1A2_1H);
      graphD.addEdge(nodeA1, nodeA2, edgeA1A2_2H);
      varietyOfOrders = new File(testResourcePath.getPath() + "/variety-of-orders.dot");
   }

   protected void tearDown() throws Exception {
       sequential.unlock();
       super.tearDown();
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
   public void testSplitAttributesOff() throws Exception {
     String in = "Investor_Name_Dinesh -> Stock_Symbol_AAPL [ label=\"TYPE=BUYLIMITORDER\" ];";
     String[] expected = new String[]{"Investor_Name_Dinesh -> Stock_Symbol_AAPL ", " label=\"TYPE=BUYLIMITORDER\" ];"};
     String[] actual = DotLanguageFile.splitAttributesOff(in);
     List<String> expectedD = new ArrayList<String>();
     List<String> actualL = new ArrayList<String>();
     Collections.addAll(expectedD, expected);
     Collections.addAll(actualL, actual);
     
     IntStream.range(0, expectedD.size())
       .forEach(idx -> {assertTrue(expectedD.get(idx).equals(actualL.get(idx)));});
   }
   public void testNodeParser() throws Exception {
     String in = "Investor_Name_Dinesh";
     HashMap<String, String> expected = new HashMap<String, String>();
     expected.put("TYPE", "Investor");
     expected.put("Name", "Dinesh");
     HashMap<String, String> actual = DotLanguageFile.nodeTextToHashMap(in);
     for (java.util.Map.Entry<String, String> kv : actual.entrySet()) {
       String k = kv.getKey();
       String v = kv.getValue();
       System.out.println("ACTUAL KEY = " + k);
       System.out.println("ACTUAL VAL = " + v);
     }
     assertTrue(expected.equals(actual));
   }
   public void testAttributeParser() throws Exception {
     String in = " label=\"TYPE=BUYLIMITORDER\" ];";
     HashMap<String, String> expected = new HashMap<String, String>();
     expected.put("TYPE", "BUYLIMITORDER");
     HashMap<String, String> actual = DotLanguageFile.parseAttributes(in);
     for (java.util.Map.Entry<String, String> kv : actual.entrySet()) {
       String k = kv.getKey();
       String v = kv.getValue();
       System.out.println("ACTUAL KEY = " + k);
       System.out.println("ACTUAL VAL = " + v);
     }
     assertTrue(expected.equals(actual));
   }
   public void testAttributeParserEmpty() throws Exception {
     String in = "];";
     HashMap<String, String> expected = new HashMap<String, String>();
     HashMap<String, String> actual = DotLanguageFile.parseAttributes(in);
     assertTrue(expected.equals(actual));
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

   // test can read a graph with one purchase
   public void testSinglePurchase() throws Exception {
      System.out.println("TESTIN SINGLE PURCHASE");
      DotLanguageFile fileC = new DotLanguageFile(onePurchase);
      assertTrue(GraphUtility.equals(
            graphC,
            fileC.parse()
      ));
   }

   // test can read a graph with two purchase
   public void testTwoPurchase() throws Exception {
      System.out.println("TESTIN COMPLEX PURCHASE");
      DotLanguageFile fileD = new DotLanguageFile(varietyOfOrders);
      assertTrue(GraphUtility.equals(
            graphD,
            fileD.parse()
      ));
   }
}
