public class Main {

   public static void main(String[] args) {
      System.out.println("Initialize TraceAR-Server");
      Controller myController=Controller.getInstance();
      GUI myGUI = new GUI();
      myController.setGUI(myGUI);
      MultiThreadedServer myServer = new MultiThreadedServer(myController);
      myController.setServer(myServer);
      
      
   }
}
