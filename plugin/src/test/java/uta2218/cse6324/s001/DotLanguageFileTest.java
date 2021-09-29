package uta2218.cse6324.s001;

import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.HashMap;
import java.util.Set;

public class DotLanguageFileTest extends TestCase {
   protected Graph<HashMap<String, String>, DefaultEdge> emptyGraphExpected;

   // assigning the values
   protected void setUp(){
      emptyGraphExpected = DotLanguageFile.createEmptyGraph();
   }

   // test empty graph is actually empty
   public void testEmpty(){
      Set<HashMap<String, String>> vertices = emptyGraphExpected.vertexSet();
      Set<DefaultEdge> edges = emptyGraphExpected.edgeSet();
      assertTrue(vertices.isEmpty());
      assertTrue(edges.isEmpty());
   }
}
