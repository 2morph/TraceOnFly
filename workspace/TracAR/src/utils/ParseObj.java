package utils;


import java.util.ArrayList;

import math.Triangle;
import math.Vertex;


public class ParseObj {
   
   public static Vertex[] getVertizesFromSting (String objString) {
      // remove double whitespaces:
      objString = cleanString(objString);
      String[] lines = objString.split("\n");
      ArrayList<Vertex> vertices = new ArrayList<Vertex>();
      for (int lineNum=0; lineNum<lines.length; lineNum++) {
          if (lines.length > 0) {
             String line = lines[lineNum];
              if (line.startsWith("#")) {
                  //ignore comments
              } else if (line.startsWith("v ")) {
                  //add new vertex to vector
                 String[] values = line.split(" ");
                  vertices.add(new Vertex(
                          Float.parseFloat(values[1]),
                          Float.parseFloat(values[2]),
                          Float.parseFloat(values[3])));
              }
          }
      }
      Vertex[] vArray = new Vertex[vertices.size()];
      for(int i=0; i<vertices.size(); i++) vArray[i] = vertices.get(i);
      return vArray;
   }
   
   public static Triangle[] getTrianglesFromSting (String objString) {
      // remove double whitespaces:
      objString = cleanString(objString);
      String[] lines = objString.split("\n");
      ArrayList<Triangle> triangles = new ArrayList<Triangle>();
      for (int lineNum=0; lineNum<lines.length; lineNum++) {
          if (lines.length > 0) {
             String line = lines[lineNum];
              if (line.startsWith("#")) {
                  //ignore comments
              } else if (line.startsWith("f ")) {
                  //add new vertex to vector
                 String[] values = line.split(" ");
                  triangles.add(new Triangle(
                          Integer.parseInt(values[1]),
                          Integer.parseInt(values[2]),
                          Integer.parseInt(values[3])));
              }
          }
      }
      Triangle[] fArray = new Triangle[triangles.size()];
      for(int i=0; i<triangles.size(); i++) fArray[i] = triangles.get(i);
      return fArray;
   }

   public static float[] getLinesArray(Vertex[] vs, Triangle[] ts) {
      float[] lineArray = new float[ts.length*18];
      int i=0;
      for (Triangle t : ts) {
         lineArray[i++] = vs[t.v1-1].x;
         lineArray[i++] = vs[t.v1-1].y;
         lineArray[i++] = vs[t.v1-1].z;
         
         lineArray[i++] = vs[t.v2-1].x;
         lineArray[i++] = vs[t.v2-1].y;
         lineArray[i++] = vs[t.v2-1].z;
         
         lineArray[i++] = vs[t.v1-1].x;
         lineArray[i++] = vs[t.v1-1].y;
         lineArray[i++] = vs[t.v1-1].z;
         
         lineArray[i++] = vs[t.v3-1].x;
         lineArray[i++] = vs[t.v3-1].y;
         lineArray[i++] = vs[t.v3-1].z;
         
         lineArray[i++] = vs[t.v2-1].x;
         lineArray[i++] = vs[t.v2-1].y;
         lineArray[i++] = vs[t.v2-1].z;

         lineArray[i++] = vs[t.v3-1].x;
         lineArray[i++] = vs[t.v3-1].y;
         lineArray[i++] = vs[t.v3-1].z;

      }
      return lineArray;
   }
   
   public static float[] getLineLoopArray(Vertex[] vs, Triangle[] ts) {
      float[] lineArray = new float[ts.length*9];
      int i=0;
      for (Triangle t : ts) {
         lineArray[i++] = vs[t.v1-1].x/20;
         lineArray[i++] = vs[t.v1-1].y/20;
         lineArray[i++] = vs[t.v1-1].z/20;
         
         lineArray[i++] = vs[t.v2-1].x/20;
         lineArray[i++] = vs[t.v2-1].y/20;
         lineArray[i++] = vs[t.v2-1].z/20;
         
         lineArray[i++] = vs[t.v3-1].x/20;
         lineArray[i++] = vs[t.v3-1].y/20;
         lineArray[i++] = vs[t.v3-1].z/20;
      }
      return lineArray;
   }
   
   private static String cleanString(String s) {
      if (!s.contains("  ")) return s;     
      return cleanString( s.replace("  ", " "));
   }
}
