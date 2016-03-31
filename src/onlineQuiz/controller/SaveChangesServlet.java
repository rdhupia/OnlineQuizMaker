package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import onlineQuiz.dao.AnswerChoiceDAO;
import onlineQuiz.dao.QuestionDAO;
import onlineQuiz.dao.QuizDAO;
import onlineQuiz.model.AnswerChoice;
import onlineQuiz.model.Question;
import onlineQuiz.model.Quiz;

/**
 * Servlet implementation class SaveChangesServlet
 */
@WebServlet("/SaveChangesServlet")
public class SaveChangesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private QuestionDAO quesDao;
	private AnswerChoiceDAO ansDao;
	

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
				
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("currentQuiz");
		BigInteger quizId = BigInteger.valueOf(quiz.getQuizid());
		
		// Get All Question details
		
		String quesText = request.getParameter("quesText");
		String ansExpl = request.getParameter("ansExpl");
		int diffLevel = Integer.parseInt( request.getParameter("diff") );
		String hint = request.getParameter("hint");
		
		// If adding a new quesiton
		if( request.getParameter("addQuestion") != null) {
			BigInteger ansType = new BigInteger(request.getParameter("ansType"));
			Question newQuestion = new Question( ansExpl, diffLevel, hint, 0, quesText, ansType, quizId );
			QuestionDAO newQuesDao = new QuestionDAO( mgr.getEntityManager() );
			newQuesDao.addQuestion(newQuestion);
			mgr.closeTransaction();
			long qid = newQuestion.getQuesid();
			
			// Empty answer that can be mod later
			AnswerChoice ansChoice = new AnswerChoice( "", 1, 0, BigInteger.valueOf(qid));
			AnswerChoiceDAO ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
			ansDao.addAnswerChoice(ansChoice);
			mgr.closeTransaction();
			
			int choices = 0;
			if( ansType.intValue() == 1)
				choices = 3;
			else if ( ansType.intValue() == 2 )
				choices = 1;
			else if ( ansType.intValue() == 3 )
				choices = 0;
			else if ( ansType.intValue() == 4 )
				choices = 3;
			
			for( int i = 0; i < choices; i++ ) {
				AnswerChoice ans = new AnswerChoice( "", 0, 0, BigInteger.valueOf(qid));
				AnswerChoiceDAO ansD = new AnswerChoiceDAO( mgr.getEntityManager() );
				ansD.addAnswerChoice(ans);
				mgr.closeTransaction();
			}
			// Get updated questions
			QuestionDAO qDao = new QuestionDAO( mgr.getEntityManager() );
			List<Question> questions = qDao.getQuestionsByQuiz(quizId);
			mgr.closeTransaction();
			request.setAttribute("questions", questions);
			request.getRequestDispatcher("/questionPool.jsp").forward(request, response);
			return;
		}
		
		// If editing an existing question
		int asked = Integer.parseInt(request.getParameter("asked"));
		int qType = Integer.parseInt(request.getParameter("qType"));
		BigInteger qTypeIdBig = new BigInteger(request.getParameter("qType"));
		
		// Create question object and merge to DB
		long quesId = Integer.parseInt(request.getParameter("quesId"));
		Question question = new Question(quesId, ansExpl, diffLevel, hint, asked, quesText, qTypeIdBig, quizId);
		quesDao = new QuestionDAO( mgr.getEntityManager() );
		quesDao.updateQuestion(question);
		mgr.closeTransaction();
		
		
		// MCQs
		if( qType == 1 ) {
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");
			String ans3 = request.getParameter("ans3");
			String ans4 = request.getParameter("ans4");
			long ansId1 = Integer.parseInt(request.getParameter("ansId1"));
			long ansId2 = Integer.parseInt(request.getParameter("ansId2"));
			long ansId3 = Integer.parseInt(request.getParameter("ansId3"));
			long ansId4 = Integer.parseInt(request.getParameter("ansId4"));
			
			ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
			ansDao.updateAnswer(ans1, ansId1);
			ansDao.updateAnswer(ans2, ansId2);
			ansDao.updateAnswer(ans3, ansId3);
			ansDao.updateAnswer(ans4, ansId4);
			mgr.closeTransaction();
		} 
		// True False
		else if( qType == 2 ) {
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");	
			long ansId1 = Integer.parseInt(request.getParameter("ansId1"));
			long ansId2 = Integer.parseInt(request.getParameter("ansId2"));		
			
			ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
			ansDao.updateAnswer(ans1, ansId1);
			ansDao.updateAnswer(ans2, ansId2);
			mgr.closeTransaction();
		}
		// One Word Input
		else if( qType == 3) {
			String ans1 = request.getParameter("ans1");	
			long ansId1 = Integer.parseInt(request.getParameter("ansId1"));		
			
			ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
			ansDao.updateAnswer(ans1, ansId1);
			mgr.closeTransaction();
		}
		// Multi
		else if( qType == 4 ) {
			String correct[] = request.getParameterValues("correct");
			int correct1 = 0, correct2 = 0, correct3 = 0, correct4 = 0;
			for( int i = 0; i < correct.length; i++ ) {
				if( correct[i].equalsIgnoreCase("first") )
					correct1 = 1;
				else if ( correct[i].equalsIgnoreCase("second") )
					correct2 = 1;
				else if ( correct[i].equalsIgnoreCase("third") )
					correct3 = 1;
				else if ( correct[i].equalsIgnoreCase("fourth") )
					correct4 = 4;				
			}
				
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");
			String ans3 = request.getParameter("ans3");
			String ans4 = request.getParameter("ans4");	
			long ansId1 = Integer.parseInt(request.getParameter("ansId1"));
			long ansId2 = Integer.parseInt(request.getParameter("ansId2"));
			long ansId3 = Integer.parseInt(request.getParameter("ansId3"));
			long ansId4 = Integer.parseInt(request.getParameter("ansId4"));	
			
			ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
			ansDao.updateAnswerWithCorrectness(ans1, ansId1, correct1);
			ansDao.updateAnswerWithCorrectness(ans2, ansId2, correct2);
			ansDao.updateAnswerWithCorrectness(ans3, ansId3, correct3);
			ansDao.updateAnswerWithCorrectness(ans4, ansId4, correct4);
			mgr.closeTransaction();
		}
		
		// Get updated questions
		QuestionDAO qDao = new QuestionDAO( mgr.getEntityManager() );
		List<Question> questions = qDao.getQuestionsByQuiz(quizId);
		mgr.closeTransaction();
		request.setAttribute("questions", questions);
		request.getRequestDispatcher("/questionPool.jsp").forward(request, response);
		
	}

}
