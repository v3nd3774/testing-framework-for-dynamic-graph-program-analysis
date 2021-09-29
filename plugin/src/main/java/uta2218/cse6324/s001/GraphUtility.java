package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import java.util.HashMap;
import java.util.Set;

class GraphUtility {
    protected static boolean equals(
        Graph<HashMap<String, String>, DefaultEdge> Ga,
        Graph<HashMap<String, String>, DefaultEdge> Gb
    ) {
      Set<HashMap<String, String>> Va = Ga.vertexSet();
      Set<HashMap<String, String>> Vb = Gb.vertexSet();

      Set<DefaultEdge> Ea = Ga.edgeSet();
      Set<DefaultEdge> Eb = Gb.edgeSet();

      return Va.equals(Vb) && Ea.equals(Eb);
      }
}
