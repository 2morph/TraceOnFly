package de.manuel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       PrintWriter writer = response.getWriter();
      
       writer.println("<html>");
       writer.println("<head><title>Hello World Servlet</title></head>");
       writer.println("<body>");
       writer.println("  <h1>Hello World from a Sevlet!</h1>");
       writer.println("<body>");
       writer.println("</html>");
          
       writer.close();         
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   PrintWriter writer = response.getWriter();
	   String sum1= request.getParameter("value1");
      String sum2 = request.getParameter("value2");
      writer.println("Hallo du Arsch, das Ergebnis ist: " + (Integer.parseInt(sum1)+Integer.parseInt(sum2)));
      
         
      writer.close();         
	}

}
