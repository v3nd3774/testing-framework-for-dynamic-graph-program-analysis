
package uta2218.cse6324.s001;

import junit.framework.TestCase;
import java.util.HashMap;

public class HashMapEdgeTest extends TestCase {
   protected HashMap<String, String> a;
   protected HashMap<String, String> b;
   protected HashMap<String, String> c;

   // assigning the values
   protected void setUp(){
      HashMap<String, String> a = new HashMap<String, String>();
      HashMap<String, String> b = new HashMap<String, String>();
      HashMap<String, String> c = new HashMap<String, String>();
      a.put("KEYA", "VALUEA");
      b.put("KEYB", "VALUEB");
      c.put("KEYC", "VALUEC");
   }

   public void testInstantiate() {
     HashMapEdge edge = new HashMapEdge(a);
     assertTrue(edge instanceof HashMapEdge);
   }

   public void testGetter(){
     HashMapEdge edge = new HashMapEdge(a);
     assertTrue(edge.getLabel().equals(a));
   }

   public void testUpdate(){
     HashMapEdge edge = new HashMapEdge(a);
     edge.setLabel(b);
     assertTrue(edge.getLabel().equals(b));
   }
}
