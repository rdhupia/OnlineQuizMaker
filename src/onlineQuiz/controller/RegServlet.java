package onlineQuiz.controller;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.UserDAO;
import onlineQuiz.model.*;
import onlineQuiz.security.*;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet(description = "Registration Servlet", urlPatterns = { "/RegServlet" })
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDao;
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagement mgr = new EntityManagement();
		
		// BeginEntityManager
		userDao = new UserDAO( mgr.getEntityManager() );

		// Get Parameters from registration form
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String email = request.getParameter("email");
		String conEmail = request.getParameter("conEmail");
		String password = request.getParameter("password");
		String conPassword = request.getParameter("conPassword");
		int role = Integer.parseInt(request.getParameter("role"));

		// Create User Entity object
		User user = new User(email, fname, lname, password, role);
		request.setAttribute("user", user);
		
		boolean registered = false;
					
		// Email and passwords entered are confirmed, user doesn't already exist - register
		if( email.equals(conEmail) ) {
			if( password.equals(conPassword) ) {
				
				boolean userExists = true;
				// Check if a user is registered with the same email
				try {
					User temp = userDao.getUserByEmail(email);
				} catch (NoResultException e) {
					userExists = false;
				}
				if(userExists) {
					System.out.println("User already exists with email: "+email);
					request.setAttribute("userExistsError", "A user is already registered with this Email, please register with a different Email address.");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					return;
				}
				
				// Encrypt entered password
				String encryptedEnteredPwd = Security.encryptPassword(email, password);

				user.setPassword(encryptedEnteredPwd);
				// Add user to db
				userDao.addUser(user);
				registered = true;
				request.setAttribute("registered", registered);
				this.getServletContext().getRequestDispatcher("/userDetails.jsp").forward(request, response);
				
				// Commit transactiona and close EM
				mgr.closeTransaction();
				return;
			} 
			else {
				request.setAttribute("pwdMatchError", "Passwords entered do not match!");
	            request.getRequestDispatcher("/register.jsp").forward(request, response);
	            return;
			}
		}
		else {
			request.setAttribute("emailMatchError", "Emails entered do not match!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
	}
}
