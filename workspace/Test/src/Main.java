
public class Main {

   private final int startprize = 3000;
   private final int deltaUpkeep = 50;
   private final int endTime = 200000;
   private final int traceTime = 1000;
   
   private final int delta = startprize/10;
   private int cash = 10000;
   private int upkeep = 0;
   private int time = 0;
   private int prize=startprize; 
   private int buildings=0; 
 
   
   public static void main(String[] args) {
      //System.out.println("Hallo");
      new Main();
   }
   
   public Main() {
      start();
   }
   
   private void start() {
      for (time = 0; time<endTime; time++) {
         cash+=(upkeep*0.2);
         buyBuildings();
         trace();
      }
   }

   private void buyBuildings() {
      while(cash > prize) {
         buildings++;
         cash-=prize;
         prize+=delta;
         upkeep+=deltaUpkeep;
      }
   }
   
   private void trace() {
      if(time%traceTime != 0) return;
      //System.out.println("time: " + time + " buildings: " + buildings + "   upkeep: " + upkeep + "   cash: " + cash + "   prize: " + prize);
      System.out.println(upkeep);
   }
}
