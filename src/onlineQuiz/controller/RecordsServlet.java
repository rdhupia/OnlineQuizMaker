package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.RecordDAO;
import onlineQuiz.dao.UserDAO;
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
		int userrole = Integer.parseInt(request.getParameter("userrole"));
		
		List<Record> recordlist = recordDao.getRecordsByUser(userIdbig);
		float score = 0;
		for( Record record : recordlist ) {
			score += record.getScore();
		}
		score = score / recordlist.size();
		List<Quiz> quizlist = quizDao.getAllQuizzes();	
		
		if( userrole >= 4 ) {
			RecordDAO rec = new RecordDAO( mgr.getEntityManager() );
			List<Record> recs = new ArrayList<>();
			List<String> names = new ArrayList<>();
			for( Quiz quiz : quizlist ) {
				List<Record> r = rec.getRecordsByQuizAdmin( BigInteger.valueOf(quiz.getQuizid()) ) ;
				for( Record record : r) {
					UserDAO udao = new UserDAO( mgr.getEntityManager());
					names.add( udao.getUser(record.getUserId().intValue()).getFirstname()+udao.getUser(record.getUserId().intValue()).getLastname() );
				}
				recs.addAll(r);
				request.setAttribute("names", names);
			}
			recordlist = recs;
		}
		
		mgr.closeTransaction();
		
		request.setAttribute("score", score);
		request.setAttribute("records", recordlist);
		request.setAttribute("quiz", quizlist);
		request.setAttribute("pressed", 1);
		request.getRequestDispatcher("/records.jsp").forward(request, response);
		
	}

	

}























