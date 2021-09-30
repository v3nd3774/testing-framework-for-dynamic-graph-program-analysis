package uta2218.cse6324.s001;

import org.jgrapht.graph.DefaultEdge;
import java.util.HashMap;

@SuppressWarnings("serial")
class HashMapEdge extends DefaultEdge {
  private HashMap<String, String> label;
  public HashMapEdge(HashMap<String, String> label) {
    this.label = label;
  }
  public HashMap<String, String> getLabel() {
    return this.label;
  }
  public void setLabel(HashMap<String, String> label) {
    this.label = label;
  }
}
