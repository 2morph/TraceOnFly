package de.tracar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class GetImageServlet
 */
@WebServlet(description = "render raytraced image from model data and camera picture", urlPatterns = { "/GetImageServlet" })
@MultipartConfig
public class GetImageServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;
   private static final String jsonIdentifier="Json-Object-Data";
   private static final String imageIdentifier="Image-Data";
	
	
	private FileManager myFileManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	   myFileManager = FileManager.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   PythonScript myScript = new PythonScript("testscript.py");
	   myScript.create(myFileManager.getPath() + "cam.jpg");
	   myScript.save();
	   myScript.executeWithBlender();
	   PrintWriter writer = response.getWriter();
      writer.println("<html>");
      writer.println("<head><title>Hello World Servlet huhu </title></head>");
      writer.println("<body>");
      writer.println("  <h1>Hello World from a Sevlet! </h1>");
      writer.println("<body>");
      writer.println("</html>");
      writer.close();  
      //render();
      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String sessionID = request.getSession(true).getId();
      
	   String jsonString="";
	   String responseString="\n";
	   boolean jsonReceived=false;
	   boolean imageReceived=false;
	   int imageSize=0;
	   JSONObject objectData;
	   
	  
	   for (Part part : request.getParts()) {
	        String partname = getFilename(part);
	        if (partname == null) {
	            // Process JSON-part.
	            if(part.getName().equalsIgnoreCase(jsonIdentifier)) {
	               jsonString = getValue(part);
                  try {
                     objectData = new JSONObject(jsonString);
                     imageSize=objectData.getInt("imageSize");
                  } catch (JSONException e) {
                     break;
                  }
                  jsonReceived=true;
	            } 
	        } else if (!partname.isEmpty() && jsonReceived) {
	            if(part.getName().equalsIgnoreCase(imageIdentifier)) {
	               //filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	               InputStream filecontent = part.getInputStream();
	               String filename = "cam_" + sessionID + ".jpg";
	               myFileManager.saveFileFromStream(filecontent, imageSize, filename);
	               imageReceived=true;
	            }
	        }
	    }
	   PrintWriter writer = response.getWriter();
	   if(jsonReceived) responseString+="\nreceived data: " + jsonString;
	   if(imageReceived) responseString+="\nimage Received";
	   writer.println(responseString);
      writer.close();    
      if(jsonReceived && imageReceived) {
         render();
         System.out.println("yeah");
         System.out.println("ID: " + sessionID);
      }
	}
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}
	
	private void render() {
	   myFileManager.execBlender(); 
	}

}
