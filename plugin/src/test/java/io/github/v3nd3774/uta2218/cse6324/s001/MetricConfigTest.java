package io.github.v3nd3774.uta2218.cse6324.s001;

import junit.framework.TestCase;
import org.jgrapht.Graph;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.io.File;

public class MetricConfigTest extends TestCase {

   private File metricFile;
   private File testResourcePath;
   private String expectedQuery;
   private TimeUnit expectedTimeUnit;
   private Integer expectedCycles;
   private Integer expectedInterval;
   private String expectedComparison;

   // assigning the values
   protected void setUp(){
      testResourcePath = new File("src/test/resources");
      // Setup empty graph file
      metricFile = new File(testResourcePath.getPath() + "/metric1.properties");
      expectedQuery = "match (n) return n";
      expectedTimeUnit = TimeUnit.SECONDS;
      expectedCycles = 90;
      expectedInterval = 15;
      expectedComparison = "<";
   }

   public void testMetrics(){
     MetricConfig conf = new MetricConfig(metricFile);
     conf.parse();
     assertEquals(expectedCycles, conf.cycles);
     assertEquals(expectedInterval, conf.interval);
     assertEquals(expectedComparison, conf.comparison);
     assertEquals(expectedQuery, conf.query);
     assertEquals(expectedTimeUnit, conf.timeunit);
   }
}
