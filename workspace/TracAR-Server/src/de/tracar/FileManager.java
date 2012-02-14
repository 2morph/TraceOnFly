package de.tracar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileManager {
   
   private static final String PATH="C:\\test\\";
   
   private static FileManager INSTANCE; 
   
   private FileManager() {}
   
   public static FileManager getInstance() {
      if (INSTANCE != null) return INSTANCE;
      return new FileManager();
   }
   
   public String getPath() { return PATH; }
  
   
   protected void execBlender() {
      try {
         String cmd = new String("cmd /C \"" + PATH + "start.bat\"");
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
     
//   protected void saveCameraPicture(byte[] imageString2) {
//      
//         ByteArrayOutputStream bStrm = new ByteArrayOutputStream();
//        
//         try {
////            for(int i=0,l=imageString.length(); i<l ; i++) {
////               bStrm.write(imageString.charAt(i));
////            }
////            byte [] mybytearray  = bStrm.toByteArray();//new byte [(int)imageSize];
//            byte[] mybytearray = imageString2;
//            FileOutputStream fos = new FileOutputStream("C:\\test\\pups.jpg");
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            bos.write(mybytearray, 0 , mybytearray.length);
//            bos.flush();
//            bos.close();
//            bStrm.close();
//
//         } catch (IOException e) {
//           e.printStackTrace();
//         }
//         
//         
//   }
   
   protected boolean saveFileFromStream(InputStream inputStream, int fileSize, String filename) {
      try {
         File f=new File(PATH+filename);
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
   
   protected boolean saveFileFromString(String content, String filename) {
      try {
         FileWriter fstream = new FileWriter(PATH+filename);
         BufferedWriter out = new BufferedWriter(fstream);
         out.write(content);
         out.close();
      } catch (IOException e) {
         return false;
      } 
      return true;
   }

}
