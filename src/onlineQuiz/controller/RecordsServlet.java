package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.RecordDAO;
import onlineQuiz.dao.QuizDAO;
import onlineQuiz.model.Quiz;
import onlineQuiz.model.Record;

/**
 * Servlet implementation class RecordsServlet
 */
@WebServlet(description = "Displays User's Quiz Record", urlPatterns = { "/RecordsServlet" })
public class RecordsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private RecordDAO recordDao;
	private QuizDAO quizDao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagement mgr = new EntityManagement();
		recordDao = new RecordDAO(mgr.getEntityManager());
		quizDao = new QuizDAO(mgr.getEntityManager());
		
		//int userId = Integer.parseInt(request.getParameter("userId"));
		BigInteger userIdbig = new BigInteger(request.getParameter("userId"));
		
		List<Record> recordlist = recordDao.getRecordsByUser(userIdbig);		
		List<Quiz> quizlist = quizDao.getAllQuizzes();	
		
		mgr.closeTransaction();
		
		request.setAttribute("records", recordlist);
		request.setAttribute("quiz", quizlist);
		request.setAttribute("pressed", 1);
		request.getRequestDispatcher("/records.jsp").forward(request, response);
		
	}

	

}























