package io.github.v3nd3774.uta2218.cse6324.s001;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import java.util.stream.IntStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.nio.file.Files;

public class MetricConfig {
    private File input;
    private Boolean parsed;


    private Properties props;
    public String query;
    public TimeUnit timeunit;
    public Integer cycles;
    public Integer interval;
    public String comparison;

    public void parse() {
      if(!this.parsed) {
        try {
          this.props.load(new FileInputStream(this.input));

          this.timeunit = TimeUnit.valueOf(this.props.getProperty("metric.unit"));
          this.query = this.props.getProperty("metric.query");
          this.cycles = Integer.parseInt(this.props.getProperty("metric.cycles"));
          this.interval = Integer.parseInt(this.props.getProperty("metric.interval"));
          this.comparison = this.props.getProperty("metric.comparison");

          this.parsed = true;
        } catch (Exception e) {
          System.out.println("ERROR PARSING METRICS!");
        }
      }
    }
    public MetricConfig (File input) {
      this.input = input;
      this.props = new Properties();
      this.parsed = false;
    }
}
