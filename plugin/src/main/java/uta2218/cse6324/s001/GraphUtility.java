package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import java.util.HashMap;
import java.util.Set;

class GraphUtility {
    protected static boolean equals(
        Graph<HashMap<String, String>, HashMapEdge> Ga,
        Graph<HashMap<String, String>, HashMapEdge> Gb
    ) {
      Set<HashMap<String, String>> Va = Ga.vertexSet();
      Set<HashMap<String, String>> Vb = Gb.vertexSet();

      Set<HashMapEdge> Ea = Ga.edgeSet();
      Set<HashMapEdge> Eb = Gb.edgeSet();

      return Va.equals(Vb) && Ea.equals(Eb);
   }
}
