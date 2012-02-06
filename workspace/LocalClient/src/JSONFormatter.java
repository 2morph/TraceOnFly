import org.json.JSONException;
import org.json.JSONObject;


public class JSONFormatter {
   
   JSONObject myJSON;
   
   public JSONFormatter() {
      myJSON = new JSONObject();
      setValues();
   }
   
   public void setValues() {
      
      try {
         myJSON.put("imageSize", 2430);
         myJSON.put("x-value", 3);
         myJSON.put("y-value", 4);
         myJSON.put("z-value", 5);
      } catch (JSONException e) {
        
         e.printStackTrace();
      }
   }
   
   
   public JSONObject getJSON() {
      return myJSON;
   }

}
