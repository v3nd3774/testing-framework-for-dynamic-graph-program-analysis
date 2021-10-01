package uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import java.util.stream.IntStream;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
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
    protected static String[] splitAttributesOff(String trimmed) {
      String left = trimmed.split("\\[")[0];
      String attributes = trimmed.split("\\[")[1];
      return new String[]{left, attributes};
    }
    protected static HashMap<String, String> parseAttributes(String in) {
      HashMap<String, String> out = new HashMap<String, String>();
      String trimmed = in.trim();
      String right = trimmed.split("\"")[1];
      String left = right.split("\"")[0];
      String[] attributes = left.split(",");
      List<String> attrTextPairs = new ArrayList<String>();
      Collections.addAll(attrTextPairs, attributes);
      for(String textPair : attrTextPairs) {
        String[] kvArray = textPair.split("=");
        String k = kvArray[0];
        String v = kvArray[1];
        out.put(k, v);
      }
      return out;
    }
    protected static HashMap<String, String> nodeTextToHashMap(String text) throws Exception {
          HashMap<String, String> nodeData = new HashMap<String, String>();
          int max_supported_node_data = 10;
          String[] elems = text.split("_", max_supported_node_data - 1);
          String type = elems[0];
          nodeData.put("TYPE", type);
          IntStream.range(1, (int)java.lang.Math.ceil(elems.length / 2.0))
            .forEach(idx -> {
              int k = idx * 2 - 1;
              int v = idx * 2;
              nodeData.put(elems[k], elems[v]);
            });
          return nodeData;
    }
    protected static Graph<HashMap<String, String>, HashMapEdge> parse(String dotFileContents) throws Exception {
      Graph<HashMap<String, String>, HashMapEdge> out = DotLanguageFile.createEmptyGraph();
      String[] linesR = dotFileContents.split(System.lineSeparator());
      List<String> lines = new ArrayList<String>();
      Collections.addAll(lines, linesR);
      String header = "digraph G {";
      String footer = "}";
      for (String line : lines.stream()
          .filter(line -> !line.equals(header))
          .filter(line -> !line.equals(footer))
          .collect(Collectors.toList())) {
        String trimmed = line.trim();
        if(line.contains("->")) {
          // EDGE DESCRIPTION LINE
          System.out.println("IDENTIFIED EDGE LINE: " + trimmed);
          String[] split = DotLanguageFile.splitAttributesOff(trimmed);
          String edge_elements = split[0];
          String edge_attributes = split[1];


          String[] nodes = edge_elements.split("->");
          String sourceNodeStr = nodes[0].trim();
          String targetNodeStr = nodes[1].trim();

          HashMap<String, String> sourceNodeData = DotLanguageFile.nodeTextToHashMap(sourceNodeStr);
          HashMap<String, String> targetNodeData = DotLanguageFile.nodeTextToHashMap(targetNodeStr);
          HashMap<String, String> edgeAttributes = DotLanguageFile.parseAttributes(edge_attributes);
          HashMapEdge edge = new HashMapEdge(edgeAttributes);
          out.addEdge(sourceNodeData, targetNodeData, edge);
        } else if (line.contains("];")){
          // NODE DESCRIPTION LINE
          System.out.println("IDENTIFIED NODE LINE: " + trimmed);
          String[] split = DotLanguageFile.splitAttributesOff(trimmed);
          String node_elements = split[0];
          String node_attributes = split[1];
          HashMap<String, String> nodeData = DotLanguageFile.nodeTextToHashMap(node_elements);
          HashMap<String, String> nodeAttributes = DotLanguageFile.parseAttributes(node_attributes);
          nodeData.putAll(nodeAttributes);
          out.addVertex(nodeData);
        } else {
          throw new Exception("UNKNOWN LINE TYPE ENCOUNTERED: " + trimmed);
        }
      }
      return out;
    }
    public Graph<HashMap<String, String>, HashMapEdge> parse() throws Exception {
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
