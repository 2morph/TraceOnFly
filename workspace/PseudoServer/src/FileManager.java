import java.io.IOException;
import java.io.InputStream;


public class FileManager {
   
   public static FileManager INSTANCE; 
   
   private FileManager() {}
   
   public static FileManager getInstance() {
      if (INSTANCE != null) return INSTANCE;
      return new FileManager();
   }
   
   protected void execBlender() {
      try {
         String cmd = new String("cmd /C \"C:\\Users\\admin\\Documents\\uni\\Bachelor\\workspace\\Blender\\start.bat\"");
         Process child = Runtime.getRuntime().exec(cmd);
         InputStream in = child.getInputStream();
         int c;
         while ((c = in.read()) != -1) {
             System.out.print((char)c);
         }
         in.close();
       } catch (IOException e) {
         e.printStackTrace();
       }
   }
      

}
