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

import onlineQuiz.dao.QuizDAO;
import onlineQuiz.dao.RecordDAO;
import onlineQuiz.dao.SubjectDAO;
import onlineQuiz.model.Quiz;
import onlineQuiz.model.Record;
import onlineQuiz.model.Subject;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SubjectDAO subDao;
	private QuizDAO quizDao;
	private RecordDAO recDao;
    
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
		
		if( request.getParameter("create") != null) {
			subDao = new SubjectDAO( mgr.getEntityManager() );
			List<Subject> subjects = subDao.getAllSubjects();
			request.setAttribute("subjects", subjects);
			request.getRequestDispatcher("/createQuiz.jsp").forward(request, response);
			return;
		}
		// Get parameters
		String name = request.getParameter("quizName");
		int diff = Integer.parseInt(request.getParameter("diff"));
		int med = Integer.parseInt(request.getParameter("med"));
		int easy = Integer.parseInt(request.getParameter("easy"));
		String desc = request.getParameter("desc");
		String subject = "";
		long subId;
		if( request.getParameter("subjectChoice").equals("new") ) {
			subject = request.getParameter("newSub");
			System.out.println("Subject NEW: " + subject );
			desc = request.getParameter("newSubDesc");
			Subject sub = new Subject(desc, name);
			subDao = new SubjectDAO( mgr.getEntityManager() );
			subDao.addSubject(sub);
			mgr.closeTransaction();
			subId = sub.getSubjectid();			
		}
		else {
			subId = Integer.parseInt(request.getParameter("subjectChoice"));
		}
		Quiz newQuiz = new Quiz( diff, easy, med, desc, name, BigInteger.valueOf(subId));
		quizDao = new QuizDAO( mgr.getEntityManager() );
		quizDao.addQuiz(newQuiz);
		mgr.closeTransaction();
		BigInteger quizId = BigInteger.valueOf(newQuiz.getQuizid());
		BigInteger userId = new BigInteger(request.getParameter("userId"));
		Record record = new Record( quizId, -1, userId);
		recDao = new RecordDAO( mgr.getEntityManager() );
		recDao.addRecord(record);
		mgr.closeTransaction();

		RecordDAO recordDao = new RecordDAO( mgr.getEntityManager() );
		quizDao = new QuizDAO( mgr.getEntityManager() );
		
		// Get all records for user
		List<Record> records = recordDao.getRecordsByUser(userId);
		List<Quiz> quizzes = new ArrayList<>();
		for( Record r : records ) {
			long qId = r.getQuizId().longValue();
			quizzes.add( quizDao.getQuiz(qId) );
		}
		// Sending quizzes to jsp file
		if(quizzes != null) {
			request.setAttribute("quizzes", quizzes);
			request.getRequestDispatcher("/quizzes.jsp").forward(request, response);
			return;
		}
	}

}
