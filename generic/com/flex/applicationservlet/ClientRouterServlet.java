
package com.flex.applicationservlet;
import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.Enumeration;
//import com.flex.ejbsrcinter.CompanyPortal;
import javax.ejb.*;

    public class ClientRouterServlet extends HttpServlet {
	
	//@EJB private CompanyPortal clientBean; 
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			String user_agent = request.getHeader("User-Agent");
			if(user_agent != null){
				RequestDispatcher report =  request.getRequestDispatcher("appclient.html");
				report.forward(request,response);
			}
	
		/*String requestUsername = request.getParameter("username");
		String requestPassword = request.getParameter("password");
		
		ServletUtilities utility = new ServletUtilities();
		byte [] strToByte = utility.getStrToByte(requestUsername, requestPassword);
		String clientApplicationPage = utility.getClientPage(clientBean.varifiedPassword(strToByte));
		
		if(clientApplicationPage != null){
			RequestDispatcher report =  request.getRequestDispatcher(clientApplicationPage+".jsp");//call to individual client servlet
		    report.forward(request,response);
		}
		else{

		/*}*/

	}
 }

