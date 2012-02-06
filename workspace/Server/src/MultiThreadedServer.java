import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;



public class MultiThreadedServer extends Thread{
   
   private final String authentificationString = "auth01234";
   
   private ServerSocket m_ServerSocket;
   private int myPort = 8888;
   private ArrayList<ClientServiceThread> threads= new ArrayList<ClientServiceThread>();
   private Controller myController;
   
   //-------------------------------------------------------------------------
   public MultiThreadedServer(Controller theController) {
      super();
      myController = theController;
   }
   //-------------------------------------------------------------------------
   public void setPort(int thePort) {
      myPort = thePort;
   }
   public void run() {
       try { 
           m_ServerSocket = new ServerSocket(myPort); 
       } catch(IOException ioe) { 
           myController.message("Could not create server socket at " + myPort + ". Quitting."); 
           System.exit(-1); 
       } 
       myController.message("Listening for clients on port: "+myPort); 
       int id = 0; 
       while(true) {                         
           try  { 
               Socket clientSocket = m_ServerSocket.accept(); 
               ClientServiceThread clientThread = new ClientServiceThread(clientSocket, id++); 
               threads.add(clientThread);
               clientThread.start(); 
           } catch(IOException ioe) { 
              myController.message("Exception encountered on accept. Ignoring. Stack Trace :"); 
               ioe.printStackTrace(); 
           } 
       } 
   }
   //-------------------------------------------------------------------------
   //---client workers--------------------------------------------------------
   //-------------------------------------------------------------------------
   class ClientServiceThread extends Thread { 
       Socket m_clientSocket;      
       InputStream is;
       
       int m_clientID = -1; 
       boolean connected = true; 
       boolean retrievedJSON = false;
       boolean retrievedImage = false;
       
       ClientServiceThread(Socket s, int clientID) { 
           m_clientSocket = s; 
           m_clientID = clientID; 
       } 
       //-------------------------------------------------------------------------
       public void run()  {             
           BufferedReader in = null;  
           PrintWriter out = null; 
           myController.message("\n\nAccepted Client : ID - " + m_clientID + " : Address - " +  
                            m_clientSocket.getInetAddress().getHostName()); 
           try {                                 
               in = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream())); 
               out = new PrintWriter(new OutputStreamWriter(m_clientSocket.getOutputStream())); 
               is = m_clientSocket.getInputStream();
               
               String clientCommand = in.readLine(); 
               if(!clientCommand.equalsIgnoreCase(authentificationString)) {
                  message("Authentification fails: " + clientCommand);
                  connected=false;
               }else {
                  message("Authentification accepted");
               }
               
               clientCommand=in.readLine();
               if(clientCommand.equalsIgnoreCase("wantTrace")) {
                  long imageSize=0;
                  try {
                     // JSONObject.testValidity(clientCommand);
                     JSONObject myJSON = new JSONObject(in.readLine());
                      message("...JSON Object Data retrieved");
                      imageSize = myJSON.getLong("imageSize");
                      message("Image size: " + imageSize + " byte");
                      
                   } catch(JSONException e) {
                      message("Error parsing JSON Object DATA");
                      out.println("no or wrong model Data given"); 
                      out.println("quit"); 
                      out.flush(); 
                   }
                  //in.close();
                   message("... load camera image");
                   if(!loadImage((int)imageSize)) message("loading failed");
               }
               clientCommand=in.readLine();
               if(clientCommand.equalsIgnoreCase("quit") || clientCommand == null) { 
                  connected = false;    
                  message("Stopping client thread for client : " + m_clientID); 
                  out.println("quit"); 
                  out.flush(); 
              } else { 
                  // Echo it back to the client. 
                  out.println("Server hat erhalten: " + clientCommand); 
                  out.println("quit"); 
                  out.flush(); 
              } 
//               while(connected) {                     
//                   clientCommand = in.readLine(); 
//                   myController.message("Client Says :" + clientCommand); 
//                   
//                   try {
//                     // JSONObject.testValidity(clientCommand);
//                      myJSON = new JSONObject(clientCommand);
//                      message(myJSON.get("x-value").toString());
//                   } catch(JSONException e) {
//                      message("not JSON");
//                   }
//                   
//                   
//               } 
           } 
           catch(Exception e) { 
               e.printStackTrace(); 
           } finally { 
               try {                     
                   in.close(); 
                   out.close(); 
                   m_clientSocket.close(); 
                   message("...Stopped"); 
               } catch(IOException ioe) { 
                   ioe.printStackTrace(); 
               } 
           } 
       }
       //-------------------------------------------------------------------------
       //---- source: http://www.rgagnon.com/javadetails/java-0542.html [30.01.2012]
       //-------------------------------------------------------------------------
       
       private boolean loadImage(int imageSize) {
          byte [] mybytearray  = new byte [(int)imageSize];
          int bytesRead;
          int current = 0;
          try {
             InputStream is = m_clientSocket.getInputStream();
             bytesRead = is.read(mybytearray,0,mybytearray.length);
             FileOutputStream fos = new FileOutputStream("C:\\test\\test.jpg");
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             current = bytesRead;
             message("begin");
             do {
                bytesRead =
                   is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
                message(""+current);
                
             } while(current<imageSize);
             message("end");
             bos.write(mybytearray, 0 , current);
             bos.flush();
             bos.close();

          } catch (IOException e) {
            e.printStackTrace();
            return false;
          }
          return true;
       }
       //-------------------------------------------------------------------------
       private void message(String text) {
          myController.message(text);
       }
   } 
} 