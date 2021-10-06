package io.github.v3nd3774.uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;
import java.util.HashMap;

public class GraphUtilityTest extends TestCase {
   protected Graph<HashMap<String, String>, HashMapEdge> emptyGraphA;
   protected Graph<HashMap<String, String>, HashMapEdge> emptyGraphB;
   protected Graph<HashMap<String, String>, HashMapEdge> graphC;
   protected Graph<HashMap<String, String>, HashMapEdge> graphD;

   // assigning the values
   protected void setUp(){
      emptyGraphA = DotLanguageFile.createEmptyGraph();
      emptyGraphB = DotLanguageFile.createEmptyGraph();
      graphC = DotLanguageFile.createEmptyGraph();
      graphD = DotLanguageFile.createEmptyGraph();
      HashMap<String, String> nodeC = new HashMap<String, String>();
      HashMap<String, String> nodeD = new HashMap<String, String>();
      nodeC.put("KEYC", "VALUEC");
      nodeD.put("KEYD", "VALUED");
      graphC.addVertex(nodeC);
      graphC.addVertex(nodeD);
   }

   public void testEmptyEquality(){
      assertTrue(GraphUtility.equals(emptyGraphA, emptyGraphB));
   }

   public void testNonemptyInequality(){
      assertTrue(!GraphUtility.equals(graphC, graphD));
   }

   public void testNonemptyEquality(){
      assertTrue(GraphUtility.equals(graphC, graphC));
      assertTrue(GraphUtility.equals(graphD, graphD));
   }
}
