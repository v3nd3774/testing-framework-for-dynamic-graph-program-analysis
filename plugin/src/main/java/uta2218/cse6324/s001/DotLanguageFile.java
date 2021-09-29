package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.HashMap;

class DotLanguageFile {
    protected static Graph<HashMap<String, String>, DefaultEdge> createEmptyGraph() {
        Graph<HashMap<String, String>, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        return g;
      }
}
