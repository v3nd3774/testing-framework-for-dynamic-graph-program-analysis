package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

class GraphUtility {
    protected static boolean equals(
        Graph<HashMap<String, String>, HashMapEdge> Ga,
        Graph<HashMap<String, String>, HashMapEdge> Gb
    ) {
      Set<HashMap<String, String>> Va = Ga.vertexSet();
      Set<HashMap<String, String>> Vb = Gb.vertexSet();
      for (HashMap<String, String> map : Va) {
        System.out.println("Graph A Map START");
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
          String k = entry.getKey();
          String v = entry.getValue();
          System.out.println("K=" + k +"\tV=" + v);
        }
        System.out.println("Graph A Map END");
      }
      for (HashMap<String, String> map : Vb) {
        System.out.println("Graph B Map START");
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
          String k = entry.getKey();
          String v = entry.getValue();
          System.out.println("K=" + k +"\tV=" + v);
        }
        System.out.println("Graph B Map END");
      }

      Set<HashMapEdge> Ea = Ga.edgeSet();
      for (HashMap<String, String> map : Ea.stream()
                                           .map((HashMapEdge edge) -> edge.getLabel())
                                           .collect(Collectors.toList())) {
        System.out.println("Graph A Edge Map START");
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
          String k = entry.getKey();
          String v = entry.getValue();
          System.out.println("K=" + k +"\tV=" + v);
        }
        System.out.println("Graph A Edge Map END");
      }

      Set<HashMapEdge> Eb = Gb.edgeSet();
      for (HashMap<String, String> map : Eb.stream()
                                           .map((HashMapEdge edge) -> edge.getLabel())
                                           .collect(Collectors.toList())) {
        System.out.println("Graph B Edge Map START");
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
          String k = entry.getKey();
          String v = entry.getValue();
          System.out.println("K=" + k +"\tV=" + v);
        }
        System.out.println("Graph B Edge Map END");
      }
      return Va.equals(Vb) && Ea.equals(Eb);
   }
}
