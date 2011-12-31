package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class FileManager {

   private static AssetManager assetManager;
   
   public FileManager(Context context) {
      assetManager = context.getAssets();
      
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
}
