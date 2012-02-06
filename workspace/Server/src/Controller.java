import java.io.File;

import org.json.JSONObject;


public class Controller {
   //-------------------------------------------------------------------------
   // statics for Singelton
   private static Controller INSTANCE;
   public static Controller getInstance() {
      if(INSTANCE != null) return INSTANCE;
      return INSTANCE=new Controller();
   }
   //-------------------------------------------------------------------------
   private GUI myGUI;
   private MultiThreadedServer myServer;
   //-Constructor-------------------------------------------------------------
   private Controller() {
   }
   //-------------------------------------------------------------------------
   public void setGUI(GUI theGUI) {
      myGUI = theGUI;
   }
   //-------------------------------------------------------------------------
   public void setServer(MultiThreadedServer theServer) {
      myServer = theServer;
   }
   //---send message to GUI---------------------------------------------------   
   public void message(String text) {
      if(myGUI==null) return;
      myGUI.message(text);
   }
   //-------------------------------------------------------------------------
   public void startServer() {
      if(myServer == null) return;
      int port= myGUI.getPort();
      if (port > 0) {
         myServer.setPort(port);
         myServer.start();
      }
   }
   //-------------------------------------------------------------------------
   public boolean requestRaytracer(JSONObject ModelData, File cameraImage) {
      message("client ask for raytraced picture..."); 
      return true;
   }
}
