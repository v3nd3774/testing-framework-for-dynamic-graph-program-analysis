package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.nio.file.Files;

public class DotLanguageFile {
    private File input;
    private boolean readB = false;
    private boolean parsed = false;
    private String content;
    private Graph<HashMap<String, String>, HashMapEdge> graph;

    protected static Graph<HashMap<String, String>, HashMapEdge> createEmptyGraph() {
        Graph<HashMap<String, String>, HashMapEdge> g = new SimpleGraph<>(HashMapEdge.class);
        return g;
      }
    protected static Graph<HashMap<String, String>, HashMapEdge> parse(String dotFileContents) {
      // parse here
      return DotLanguageFile.createEmptyGraph();
    }
    public Graph<HashMap<String, String>, HashMapEdge> parse() {
      if(this.parsed) {
        return this.graph;
      } else {
        if(!this.readB) {
          this.read();
        }
        this.graph = DotLanguageFile.parse(this.content);
        this.parsed = true;
        return this.graph;
      }
    }
    public DotLanguageFile (File input) {
      this.input = input;
    }
    public String read() {
      if(!this.readB){
        try {
          this.content = Files.readString(this.input.toPath(), StandardCharsets.US_ASCII);
        } catch (Exception e) {
          this.content = e.toString();
        } finally {
          System.out.println(this.content);
        }
        this.readB = true;
      }
      return this.content;
    }
}
