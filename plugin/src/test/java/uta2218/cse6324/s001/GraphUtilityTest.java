package uta2218.cse6324.s001;

import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.HashMap;
import java.util.Set;

public class GraphUtilityTest extends TestCase {
   protected Graph<HashMap<String, String>, DefaultEdge> emptyGraphA;
   protected Graph<HashMap<String, String>, DefaultEdge> emptyGraphB;
   protected Graph<HashMap<String, String>, DefaultEdge> graphC;
   protected Graph<HashMap<String, String>, DefaultEdge> graphD;

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
