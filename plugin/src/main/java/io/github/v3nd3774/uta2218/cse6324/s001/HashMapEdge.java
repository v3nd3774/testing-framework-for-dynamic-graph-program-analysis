package io.github.v3nd3774.uta2218.cse6324.s001;

import org.jgrapht.graph.DefaultEdge;
import java.util.HashMap;

@SuppressWarnings("serial")
public class HashMapEdge extends DefaultEdge {
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
  @Override
  public boolean equals(Object obj) {
    HashMapEdge edge = (HashMapEdge)obj;
    return this.label.equals(edge.getLabel());
  }
  @Override
  public int hashCode() {
    return this.label.hashCode();
  }

  public HashMap<String, String> getSource(){
    return (HashMap<String, String>) super.getSource();
  }
  public HashMap<String, String> getTarget(){
    return (HashMap<String, String>) super.getTarget();
  }
}
