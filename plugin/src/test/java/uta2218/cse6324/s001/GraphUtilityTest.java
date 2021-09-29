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

   // assigning the values
   protected void setUp(){
      emptyGraphA = DotLanguageFile.createEmptyGraph();
      emptyGraphB = DotLanguageFile.createEmptyGraph();
   }

   // test equality between two empty graphs
   public void testEmptyEquality(){
      assertTrue(GraphUtility.equals(emptyGraphA, emptyGraphB));
   }
}
