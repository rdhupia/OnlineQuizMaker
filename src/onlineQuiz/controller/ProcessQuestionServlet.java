package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.AnswerChoiceDAO;
import onlineQuiz.dao.QuestionDAO;
import onlineQuiz.dao.QuestionTypeDAO;
import onlineQuiz.model.AnswerChoice;
import onlineQuiz.model.Question;
import onlineQuiz.model.QuestionType;

/**
 * Servlet implementation class ProcessQuestionServlet
 */
@WebServlet("/ProcessQuestionServlet")
public class ProcessQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private QuestionDAO quesDao;
	private AnswerChoiceDAO ansDao;
	private QuestionTypeDAO qtypeDao;
	
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
		
		if( request.getParameter("addNewQuestion") != null) {
			request.setAttribute("action", "add");
			request.getRequestDispatcher("/addQuestion.jsp").forward(request, response);
			return;
		}
		
		// Get question selected by questionPool.jsp / removeQuestion.jsp
		long quesId = Integer.parseInt(request.getParameter("quesId"));
		BigInteger quesIdBig = new BigInteger( request.getParameter("quesId") );
		
		ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
		List<AnswerChoice> answers = ansDao.getAnswerChoicesByQuestion(quesIdBig);
		
		// Delete Question use case
		if( request.getParameter("deleteQuestion") != null ) {
			BigInteger quizId = new BigInteger(request.getParameter("quizId"));
			QuestionDAO delQuesDao = new QuestionDAO( mgr.getEntityManager() );
			delQuesDao.removeQuestion(quesId);
			mgr.closeTransaction();
			for( AnswerChoice answer : answers ) {
				ansDao.removeAnswerChoice(answer.getAnsid());
			}
			// Get updated questions
			QuestionDAO qDao = new QuestionDAO( mgr.getEntityManager() );
			List<Question> questions = qDao.getQuestionsByQuiz(quizId);
			mgr.closeTransaction();
			request.setAttribute("questions", questions);
			request.getRequestDispatcher("/questionPool.jsp").forward(request, response);
			return;
		}
		
		quesDao = new QuestionDAO( mgr.getEntityManager() );
		Question question = quesDao.getQuestion(quesId);
		
		qtypeDao = new QuestionTypeDAO( mgr.getEntityManager() );
		QuestionType type = qtypeDao.getQuestionType(question.getQuestionType());
		
		request.setAttribute("question", question);
		request.setAttribute("type", type);
		request.setAttribute("answers", answers);
		
		if( request.getParameter("viewQuestion") != null ) {
			request.setAttribute("action", "view");
			request.getRequestDispatcher("/viewQuestion.jsp").forward(request, response);
		}
		else if ( request.getParameter("editQuestion") != null ) {
			request.setAttribute("action", "edit");
			request.getRequestDispatcher("/viewQuestion.jsp").forward(request, response);
		}
		else if ( request.getParameter("removeQuestion") != null ) {
			request.setAttribute("action", "remove");
			request.getRequestDispatcher("/removeQuestion.jsp").forward(request, response);
		}
		else {
			request.setAttribute("action", null);
			request.getRequestDispatcher("/quizzes.jsp").forward(request, response);
		}
		return;			
	}

}



































