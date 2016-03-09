package onlineQuiz.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.connection.*;
import onlineQuiz.model.*;
import onlineQuiz.security.*;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet(description = "Registration Servlet", urlPatterns = { "/RegServlet" })
public class RegServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	ConnectionPool pool = ConnectionPool.getInstance("mysql");
	Connection conn = null;
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PreparedStatement pstmt = null;
		Statement pstmt2 = null;
		ResultSet result = null;
		ResultSet result2 = null;
		User user = null;
		
		try {
			// Establish Connection
			conn = pool.getConnection();
			System.out.println("Connection to mySql database established");
			String query = "INSERT into Users (email, firstname, lastname, password, role) VALUES (?,?,?,?,?);";
			pstmt = conn.prepareStatement(query);
			int numOfRowsInserted = 0;
			Boolean registered = false;
					
			// Get Parameters from registration form
			String fname = request.getParameter("firstName");
			String lname = request.getParameter("lastName");
			String email = request.getParameter("email");
			String conEmail = request.getParameter("conEmail");
			String password = request.getParameter("password");
			String conPassword = request.getParameter("conPassword");
			int role = Integer.parseInt(request.getParameter("role"));

			user = new User(email, fname, lname, password, role);
			request.setAttribute("user", user);
						
			// emails and passwords enterd are confirmed, user doesn't already exist - register
			if( email.equals(conEmail) ) {
				if( password.equals(conPassword) ) {

					Security security = new Security();
					pstmt.setString(1, email);
					pstmt.setString(2, fname);
					pstmt.setString(3, lname);
					
					// Encryption
					password = security.encryptPassword(email, password);
					pstmt.setString(4, password);
					pstmt.setInt(5, role);
					
					// Check if already registered: same  email
					String query2 = "SELECT * FROM Users WHERE email = '"+email+"';";
					pstmt2 = conn.createStatement();
					result2 = pstmt2.executeQuery(query2);
					boolean userExists = false;
										
					// Loop through result set to see if user exists
					while(result2.next()) {
						userExists = true;
					}
					
					
					if(userExists) {
						request.setAttribute("userExistsError", "This user is already registered!");
						request.getRequestDispatcher("/register.jsp").forward(request, response);
					}
					else {
						numOfRowsInserted = pstmt.executeUpdate();
						System.out.println("numberOfRowsInerted : " + numOfRowsInserted);
												
						registered = true;
						request.setAttribute("registrerd", registered);
						this.getServletContext().getRequestDispatcher("/userDetails.jsp").forward(request, response);
					}
					
				}
				else {
					request.setAttribute("pwdMatchError", "Passwords entered do not match!");
					// get back to order.jsp page using forward
		            request.getRequestDispatcher("/register.jsp").forward(request, response);
				}
			}
			else {
				request.setAttribute("emailMatchError", "Emails entered do not match!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
			
		
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null) {
					result.close();
					result = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (pool != null)
					pool.freeConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}

	}

}
