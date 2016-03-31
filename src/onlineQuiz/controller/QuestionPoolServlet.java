package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.QuestionDAO;
import onlineQuiz.model.Question;

/**
 * Servlet implementation class QuestionPoolServlet
 */
@WebServlet("/QuestionPoolServlet")
public class QuestionPoolServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private QuestionDAO quesDao;

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
		quesDao = new QuestionDAO( mgr.getEntityManager() );
		
		// Get Parameters from quizStart.jsp (Teacher)
		BigInteger quizIdBig = new BigInteger( request.getParameter("quizId"));
		
		// Get questions
		List<Question> questions = quesDao.getQuestionsByQuiz(quizIdBig);
		
		mgr.closeTransaction();
		request.setAttribute("questions", questions);
		
		request.getRequestDispatcher("/questionPool.jsp").forward(request, response);
	}

}


























