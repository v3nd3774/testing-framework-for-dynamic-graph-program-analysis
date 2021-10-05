package com.github.v3nd3774.uta2218.cse6324.s001;

import junit.framework.TestCase;
import java.util.HashMap;

public class HashMapEdgeTest extends TestCase {
   protected HashMap<String, String> a;
   protected HashMap<String, String> b;
   protected HashMap<String, String> c;

   // assigning the values
   protected void setUp(){
      a = new HashMap<String, String>();
      b = new HashMap<String, String>();
      a.put("KEYA", "VALUEA");
      b.put("KEYB", "VALUEB");
   }

   public void testInstantiate() {
     HashMapEdge edge = new HashMapEdge(a);
     assertTrue(edge instanceof HashMapEdge);
   }

   public void testEquality() {
     HashMapEdge c = new HashMapEdge(a);
     HashMapEdge d = new HashMapEdge(a);
     assertTrue(c.equals(d));
   }

   public void testGetter(){
     HashMapEdge edge = new HashMapEdge(a);
     assertTrue(a.equals(edge.getLabel()));
   }

   public void testUpdate(){
     HashMapEdge edge = new HashMapEdge(a);
     edge.setLabel(b);
     assertTrue(b.equals(edge.getLabel()));
   }
}
