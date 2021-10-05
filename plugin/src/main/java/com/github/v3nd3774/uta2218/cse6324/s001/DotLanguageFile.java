package com.github.v3nd3774.uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.apache.commons.lang3.StringUtils;
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
      String [] split = trimmed.split("\"");
      if (split.length < 2) {
        return out;
      } else {
        String right = split[1];
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
          System.out.println("EDGE ELEMENTS: " + edge_elements);
          String edge_attributes = split[1];
          System.out.println("EDGE ATTRS: " + edge_attributes);


          String[] nodes = edge_elements.split("->");
          String sourceNodeStr = nodes[0].trim();
          System.out.println("source nodestr = " + sourceNodeStr);
          String targetNodeStr = nodes[1].trim();
          System.out.println("target nodestr = " + targetNodeStr);

          HashMap<String, String> sourceNodeData = DotLanguageFile.nodeTextToHashMap(sourceNodeStr);
          System.out.println("SOURCENODEDATA");
          for (java.util.Map.Entry<String, String> kv : sourceNodeData.entrySet()) {
            String k = kv.getKey();
            String v = kv.getValue();
            System.out.println("ACTUAL KEY = " + k);
            System.out.println("ACTUAL VAL = " + v);
          }
          System.out.println("TARGETNODEDATA");
          HashMap<String, String> targetNodeData = DotLanguageFile.nodeTextToHashMap(targetNodeStr);
          for (java.util.Map.Entry<String, String> kv : targetNodeData.entrySet()) {
            String k = kv.getKey();
            String v = kv.getValue();
            System.out.println("ACTUAL KEY = " + k);
            System.out.println("ACTUAL VAL = " + v);
          }
          System.out.println("EDGEDATA");
          HashMap<String, String> edgeAttributes = DotLanguageFile.parseAttributes(edge_attributes);
          HashMapEdge edge = new HashMapEdge(edgeAttributes);
          for (java.util.Map.Entry<String, String> kv : edge.getLabel().entrySet()) {
            String k = kv.getKey();
            String v = kv.getValue();
            System.out.println("ACTUAL KEY = " + k);
            System.out.println("ACTUAL VAL = " + v);
          }
          if ((!sourceNodeData.entrySet().isEmpty()) &&
              (!targetNodeData.entrySet().isEmpty()) &&
              (!edge.getLabel().entrySet().isEmpty())) {
            out.addEdge(sourceNodeData, targetNodeData, edge);
          }
          continue;
        } else if (StringUtils.countMatches(trimmed, "_") == 2) {
          // NODE DESCRIPTION LINE
          System.out.println("IDENTIFIED NODE LINE: " + trimmed);
          String[] split = DotLanguageFile.splitAttributesOff(trimmed);
          String node_elements = split[0];
          System.out.println("NODE ELEMENTS: " + node_elements);
          String node_attributes = split[1];
          System.out.println("NODE attributes: " + node_attributes);
          HashMap<String, String> nodeData = DotLanguageFile.nodeTextToHashMap(node_elements);
          HashMap<String, String> nodeAttributes = DotLanguageFile.parseAttributes(node_attributes);
          nodeData.putAll(nodeAttributes);
          if (!nodeData.entrySet().isEmpty()) {
            out.addVertex(nodeData);
          }
          continue;
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
