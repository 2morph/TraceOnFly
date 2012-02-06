package de.tracar;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileManager {
   
   //public static FileManager INSTANCE; 
   
   //private FileManager() {}
   
//   public static FileManager getInstance() {
//      if (INSTANCE != null) return INSTANCE;
//      return new FileManager();
//   }
//   
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
     
   protected void saveCameraPicture(byte[] imageString2) {
      
         ByteArrayOutputStream bStrm = new ByteArrayOutputStream();
        
         try {
//            for(int i=0,l=imageString.length(); i<l ; i++) {
//               bStrm.write(imageString.charAt(i));
//            }
//            byte [] mybytearray  = bStrm.toByteArray();//new byte [(int)imageSize];
            byte[] mybytearray = imageString2;
            FileOutputStream fos = new FileOutputStream("C:\\test\\pups.jpg");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(mybytearray, 0 , mybytearray.length);
            bos.flush();
            bos.close();
            bStrm.close();

         } catch (IOException e) {
           e.printStackTrace();
         }
         
         
   }
   
   protected boolean saveFileFromStream(InputStream inputStream, int fileSize, String filename) {
      try {
         File f=new File(filename);
         OutputStream out=new FileOutputStream(f);
         byte buf[]=new byte[fileSize];
         int len;
         while((len=inputStream.read(buf))>0) out.write(buf,0,len);
         out.close();
         inputStream.close(); 
      } catch (IOException e) {
            return false;
      } 
      return true;
   }

}
