package onlineQuiz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import onlineQuiz.dao.QuizDAO;
import onlineQuiz.dao.RecordDAO;
import onlineQuiz.dao.SubjectDAO;
import onlineQuiz.dao.UserDAO;
import onlineQuiz.model.Quiz;
import onlineQuiz.model.Record;
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
	private RecordDAO recordDao;
	private QuizDAO quizDao;

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

			// Learner
			if( user.getRole() < 4 ) {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			// Teacher
			else if( user.getRole() >= 4 ) {
				recordDao = new RecordDAO( mgr.getEntityManager() );
				quizDao = new QuizDAO( mgr.getEntityManager() );
				
				// Get all records for user
				List<Record> records = recordDao.getRecordsByUser(BigInteger.valueOf(user.getUserid()));
				List<Quiz> quizzes = new ArrayList<>();
				for( Record record : records ) {
					long quizId = record.getQuizId().longValue();
					quizzes.add( quizDao.getQuiz(quizId) );
				}
				// Sending quizzes to jsp file
				if(quizzes != null) {
					request.setAttribute("quizzes", quizzes);
					request.getRequestDispatcher("/quizzes.jsp").forward(request, response);
					return;
				}
				
			}
			
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























