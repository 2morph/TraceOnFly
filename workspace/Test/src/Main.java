import java.util.ArrayList;


public class Main {

   private final int startprize = 3000;
   private final int deltaIncome = 50;
   private final int endTime = 200000;
   private final int traceTime = 1000;
   
   private final int delta = startprize/10;
   private int cash = 10000;
   private int income = 0;
   private int time = 0;
   private int prize=startprize; 
  // private int buildings=0; 
 
   private ArrayList<Item> cars;
   private ArrayList<Item> parts;
   private Item referenceCar;
   
   public static void main(String[] args) {
      //System.out.println("Hallo");
      new Main();
   }
   
   public Main() {
      cars = new ArrayList<Item>();
      parts = new ArrayList<Item>();
      //calcIncome();
      initCars();
      initParts();
      this.printParts();
      System.out.println();
      this.printCars();
   }
   
   private void initParts() {
      parts.add(new Item("Stage II Nitrous",8,4,800));
      parts.add(new Item("Turbo Drivetrain",16,8,900));
      parts.add(new Item("TSC Injectors",10,7,900));
      parts.add(new Item("Custom Body Kit",8,12,1900));
      parts.add(new Item("Euro Tail Lights",13,13,2200));
      parts.add(new Item("Coilover Damper Kit",4,20,2700));
      parts.add(new Item("Dynamometer",24,12,5000));
      parts.add(new Item("Street Legal Slicks",7,26,5400));
      
   }
   
   private void initCars() {
      referenceCar = new Item("Reference",22,21,0);
      //referenceCar = new Item("Reference z8",36,34,700);
      //referenceCar = new Item("Reference cl600",40,41,1400);
      //referenceCar = new Item("Reference Panamera",45,42,1800);
      
      cars.add(new Item("Alfa",19,23,70));
      cars.add(new Item("Factory5",23,21,100));
      cars.add(new Item("audi tt",20,25,200));
      cars.add(new Item("vauxhall",22,24,200));
      cars.add(new Item("fisker",26,23,200));
      cars.add(new Item("lotus exige",27,23,300));
      cars.add(new Item("audi a4",28,22,300));
      cars.add(new Item("noble m12",25,26,300));
      cars.add(new Item("bmw m3",26,27,400));
      cars.add(new Item("slk",29,25,400));
      cars.add(new Item("acura",27,30,500));
      cars.add(new Item("audi s5",31,29,500));
      cars.add(new Item("911",35,28,600));
      cars.add(new Item("bmw z8",36,34,700));
      cars.add(new Item("audi r8",33,40,900));
      cars.add(new Item("aston martin vantage",37,39,1100));
      cars.add(new Item("cl 600",40,41,1400));
      cars.add(new Item("maserati quat",39,43,1600));
      cars.add(new Item("panamera",45,42,1800));
      cars.add(new Item("aston martin vanquish",46,45,2200));
      cars.add(new Item("astra dtm",44,52,3100));
      cars.add(new Item("maserati gran t",53,48,3600));
      cars.add(new Item("tesla",48,56,4300));
      cars.add(new Item("rolls royce ghost",55,50,4700));
      cars.add(new Item("gallardo",56,51,5000));
      cars.add(new Item("carrera gt",50,60,5400));
      cars.add(new Item("benz slr",60,54,5900));
      cars.add(new Item("saleen s7",55,63,6300));
      cars.add(new Item("koenigsegg ccx",62,60,6800));
      cars.add(new Item("murcielago",75,57,7600));
      cars.add(new Item("benz slr 722",69,68,8300));
      cars.add(new Item("edonis",79,66,8100));
      cars.add(new Item("toyota gt-1",71,78,11000));
      cars.add(new Item("ascari",86,69,12300));
      cars.add(new Item("ssc",100,62,15300));
      cars.add(new Item("koenigsegg agera",91,81,15800));
      cars.add(new Item("porsche dp 962",110,68,17100));
      cars.add(new Item("panoz",95,87,19800));
      cars.add(new Item("audi r10",96,124,27000));
      cars.add(new Item("weber",135,117,32400));
   }
   
   
   private void printParts() {
      Item partReference = new Item("part Reference",0,0,0);
      int min=999999999;
      Item best = partReference;
      for (Item item : parts) {
         int[] erg = item.getToReference(partReference);
         System.out.println(erg[0] + "   " + erg[1] + "   " + erg[2] + "   - " + item.name + "("+item.speed+","+item.handling+","+item.upkeep+")");
         if (erg[0]<min) {
            best=item;
            min=erg[0];
         }
      }
      System.out.println("\nBest Part: "+min+" - "+best.name+"("+best.speed+","+best.handling+","+best.upkeep+")\n");
   }
   
   private void printCars() {
      int min=999999999;
      Item best = referenceCar;
      for (Item item : cars) {
         int[] erg = item.getToReference(referenceCar);
         if(erg[0]<0 || erg[1]<0 || erg[2]<0) continue; 
         System.out.println(erg[0]+"   "+erg[1]+"   "+erg[2]+"   - "+item.name+"("+item.speed+", "+item.handling+", "+item.upkeep+")");
         if (erg[0]<min) {
            best=item;
            min=erg[0];
         }
      }
      System.out.println("\nBest Car: "+min+" - "+best.name+"("+best.speed+","+best.handling+","+best.upkeep+")\n");
   }
   
   private void calcIncome() {
      for (time = 0; time<endTime; time++) {
         cash+=(income*0.2);
         buyBuildings();
         trace();
      }
   }

   private void buyBuildings() {
      while(cash > prize) {
        // buildings++;
         cash-=prize;
         prize+=delta;
         income+=deltaIncome;
      }
   }
   
   private void trace() {
      if(time%traceTime != 0) return;
      //System.out.println("time: " + time + " buildings: " + buildings + "   upkeep: " + upkeep + "   cash: " + cash + "   prize: " + prize);
      System.out.println(income);
   }
}
