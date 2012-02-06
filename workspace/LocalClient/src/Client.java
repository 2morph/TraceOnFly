import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;



public class Client {

   private final static int myPort = 8080;
   private final static String myURL = "localhost";
   private final static String myServlet = "/TracAR-Server/GetImageServlet";
   
  
   public static void main(String[] args) throws Exception {
      String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
      String charset = "UTF-8";
      
      JSONFormatter myFormatter = new JSONFormatter();
      File myFile = new File ("images.jpg");
      String CRLF = "\r\n"; // Line separator required by multipart/form-data.
      // Send data
      URL url = new URL("http://"+myURL+":"+myPort+myServlet);
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      PrintWriter writer = null;
      try {
         OutputStream output = conn.getOutputStream();
         writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!

         // Send JSON object data
         writer.append("--" + boundary).append(CRLF);
         writer.append("Content-Disposition: form-data; name=\"Json-Object-Data\"").append(CRLF);
         writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
         writer.append(CRLF);
         writer.append(myFormatter.getJSON().toString()).append(CRLF).flush();
         
         // Send image file
         writer.append("--" + boundary).append(CRLF);
         writer.append("Content-Disposition: form-data; name=\"Image-Data\"; filename=\"" + myFile.getName() + "\"").append(CRLF);
         writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(myFile.getName())+CRLF);
         writer.append("Content-Transfer-Encoding: binary").append(CRLF);
         writer.append(CRLF).flush();
         InputStream input = null;
         try {
            input = new FileInputStream(myFile);
            byte[] buffer = new byte[(int)myFile.length()];
            for (int length = 0; (length = input.read(buffer)) > 0;) output.write(buffer, 0, length);
            output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
         } finally {
            if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
         }
         writer.append(CRLF).flush(); // CRLF is important! It indicates end of binary boundary.
         writer.append("--" + boundary + "--").append(CRLF).flush();
         writer.close();
      } finally {
         if (writer != null) writer.close();
      }
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      
      // Get the response
      String line;
      while ((line = rd.readLine()) != null) {
      System.out.println(line);
      }
   }
}

