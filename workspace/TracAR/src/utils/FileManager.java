package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

public class FileManager {

   private static AssetManager assetManager;
   
   public final static String TRACAR_FOLDER = "/sdcard/tracar/";
   public final static String PICTURE_FILENAME = "camera.jpg";
   
   
   public FileManager(Context context) {
      
      assetManager = context.getAssets();
      System.out.println();
   }
   
  
   
   public File getFileFromAssets(String filename) {
      AssetFileDescriptor afd = null;
      try {
         afd = assetManager.openFd(filename);
        // System.out.println("#########   " + afd.);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return null;
      }
      return new File(afd.toString());
   }
   
   public static File getCameraPicture() {
      return new File(TRACAR_FOLDER + PICTURE_FILENAME);
   }
   
   public static String loadObjFromFile(String file) {
      InputStream stream = null;
      try {
          stream = assetManager.open(file);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
          StringBuilder stringBuilder = new StringBuilder();
          String line = null;
          while ((line = bufferedReader.readLine()) != null) {
             stringBuilder.append(line + "\n");
          }
          bufferedReader.close();
          return stringBuilder.toString();
      } catch (IOException e) {
          // handle
      } finally {
         if (stream != null) {
            try {
                 stream.close();
            } catch (IOException e) {}
         }
      }
      return null;
   }
   
   public static void savePicture(byte[] data) {
      new SavePhotoTask().execute(data);
   }
}   
   
class SavePhotoTask extends AsyncTask<byte[], String, String> {
    @Override
    protected String doInBackground(byte[]... jpeg) {
      File photo=
          new File(FileManager.TRACAR_FOLDER+FileManager.PICTURE_FILENAME);

      if (photo.exists()) {
        photo.delete();
      }

      try {
        FileOutputStream fos=new FileOutputStream(photo.getPath());

        fos.write(jpeg[0]);
        fos.close();
      }
      catch (java.io.IOException e) {
        Log.e("PictureDemo", "Exception in photoCallback", e);
      }

      return(null);
    }
}

