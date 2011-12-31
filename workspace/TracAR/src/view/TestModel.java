package view;

public class TestModel {
   
   public static String getDiamond() {
      return "# OBJ file created by ply_to_o# \ng Object001\n" + 
            "\n" +
            "v  1  0  0\n" +
            "v  0  -1  0\n" +
            "v  -1  0  0\n" +
            "v  0  1  0\n" +
            "v  0  0  1\n" +
            "v  0  0  -1\n" +
            "\n" +
            "f  2  1  5\n" +
            "f  3  2  5\n" +
            "f  4  3  5\n" +
            "f  1  4  5\n" +
            "f  1  2  6\n" +
            "f  2  3  6\n" +
            "f  3  4  6\n" +
            "f  4  1  6\n";
   }

}
