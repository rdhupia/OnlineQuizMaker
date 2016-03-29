package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.QuizDAO;
import onlineQuiz.model.Quiz;

/**
 * Servlet implementation class QuizzesServlet
 */
@WebServlet("/QuizzesServlet")
public class QuizzesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	private QuizDAO quizDao;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagement mgr = new EntityManagement();
		quizDao = new QuizDAO( mgr.getEntityManager() );
		
		// Get Parameters from index.jsp ----- Learner
		BigInteger subjectId = new BigInteger(request.getParameter("subjectChoice"));
		System.out.println("SubjectId: "+ subjectId);
		
		List<Quiz> quizzes;
		try {
		quizzes = quizDao.getQuizzesBySubject(subjectId);
		if(quizzes.size() < 1)
			throw new NoResultException();
		System.out.println("Quiz: " + quizzes.get(0).getQuizname());
		}catch(NoResultException e) {
			System.out.println("No Quiz Found");
			request.setAttribute("quizNotFound", "No quizzes available for the subject currently. Please try again later, or try a different subject.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			mgr.closeTransaction();
			return;
		}
		
		// Sending quizzes to jsp file
		if(quizzes != null) {
			request.setAttribute("quizzes", quizzes);
			
			request.getRequestDispatcher("/quizzes.jsp").forward(request, response);
			System.out.println("Quizzes forwarded to quizzes.jsp");
			mgr.closeTransaction();
			return;
		}
		
	}

}
