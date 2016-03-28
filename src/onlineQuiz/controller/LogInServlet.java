package onlineQuiz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import onlineQuiz.dao.SubjectDAO;
import onlineQuiz.dao.UserDAO;
import onlineQuiz.model.Subject;
import onlineQuiz.model.User;
import onlineQuiz.security.Security;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet(description = "Manages Log In", urlPatterns = { "/LogInServlet" })
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDao;
	private SubjectDAO subjectDao;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagement mgr = new EntityManagement();
		userDao = new UserDAO( mgr.getEntityManager() );
		
		// Get parameters from login.jsp
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		boolean loggedIn = false;
		
		// Encrypt entered password
		String encryptedEnteredPwd = Security.encryptPassword(email, password);
		User user;
		try {
			// Get user registered with the same email
			user = userDao.getUserByEmail(email);
			if( !user.getPassword().equals(encryptedEnteredPwd) )
				throw new NoResultException();
			else
				loggedIn = true;
		} catch (NoResultException e) {
			System.out.println("Invalid username or password entered for "+email);
			request.setAttribute("logInError", "Invalid Email or Password entered. Log in failed.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			mgr.closeTransaction();
			return;
		}
		
		if(loggedIn && (user != null) ) {
			// Create session
			HttpSession session = request.getSession();
			session.setAttribute("loggedIn", user);
			// Session expiry 1 hr
			session.setMaxInactiveInterval(60*60);
			mgr.closeTransaction();
			
			subjectDao = new SubjectDAO( mgr.getEntityManager() );
			// Get subjects available for quizzes
			List<Subject> subjects = subjectDao.getAllSubjects();
			System.out.println("SUBJECT: " + subjects.get(0).getSubjectname());
			request.setAttribute("subjects", subjects);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			
		}
		else {
			mgr.closeTransaction();
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
		}
		
	}

}























