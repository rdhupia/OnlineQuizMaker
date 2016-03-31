package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import onlineQuiz.dao.AnswerChoiceDAO;
import onlineQuiz.dao.CommentDAO;
import onlineQuiz.dao.QuestionDAO;
import onlineQuiz.dao.RecordDAO;
import onlineQuiz.dao.UserResponseDAO;
import onlineQuiz.model.AnswerChoice;
import onlineQuiz.model.Comment;
import onlineQuiz.model.Question;
import onlineQuiz.model.UserResponse;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserResponseDAO responseDao;
	private AnswerChoiceDAO ansDao;
	private QuestionDAO quesDao;
	private RecordDAO recordDao;
	private CommentDAO commentDao;
	
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
		

		// Get parameters from takeQuiz.jsp
		int quesType = Integer.parseInt(request.getParameter("typeOfQ"));
		int newIndex = Integer.parseInt(request.getParameter("newIndex"));
		int recordId = Integer.parseInt(request.getParameter("recordId"));
		BigInteger recordIdBig = new BigInteger(request.getParameter("recordId"));
		BigInteger quesIdBig = new BigInteger( request.getParameter("quesId"));
		System.out.println("New Index: " + newIndex);
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<List<AnswerChoice>> answers = (List<List<AnswerChoice>>) session.getAttribute("quizAnswers");
		
		System.out.println("AnswersList: " + answers.size());
		for( int i = 0; i < answers.size(); i++)
			System.out.println("TakeQuizServlet: " +  answers.get(i).get(0).getAnswer());
		List<AnswerChoice> answerGroup = answers.get(newIndex-1);
		
		@SuppressWarnings("unchecked")
		List<Question> quests = (List<Question>) session.getAttribute("quizQuestions");
		for( int i = 0; i < quests.size(); i++)
			System.out.println("TakeQuizServlet: " +  quests.get(i).getQuestion());
		
		// Get Answers given
		int ansId = 0;
		UserResponse userResponse;
		if( quesType == 1 || quesType ==2) {
			if( request.getParameter("ansId") != null )
				ansId = Integer.parseInt(request.getParameter("ansId"));
			else
				ansId = 93;
			userResponse = new UserResponse( BigInteger.valueOf(ansId), quesIdBig, recordIdBig );
			responseDao = new UserResponseDAO( mgr.getEntityManager());
			responseDao.addUserResponse(userResponse);
			mgr.closeTransaction();
			System.out.println("Type 1/2: " + ansId);
		}
		else if( quesType == 3 ) {
			if( request.getParameter("ans") != null && answerGroup.get(0).getAnswer().equalsIgnoreCase(request.getParameter("ans"))) {
				ansId = (int) answerGroup.get(0).getAnsid();
				System.out.println("Correct Ans: " + answerGroup.get(0).getAnswer());
			}
			else
				ansId = 93;	    // Generic Wrong Answer in DB
			userResponse = new UserResponse( BigInteger.valueOf(ansId), quesIdBig, recordIdBig );
			responseDao = new UserResponseDAO( mgr.getEntityManager());
			responseDao.addUserResponse(userResponse);
			mgr.closeTransaction();
			System.out.println("Type 3: " + ansId);
		}
		else if( quesType == 4 ) {
			
			if(request.getParameterValues("ansIds") == null ) {
				ansId = 93;
				userResponse = new UserResponse(BigInteger.valueOf(ansId), quesIdBig, recordIdBig );
			} else {
				String[] ansIds = request.getParameterValues("ansIds");
				for( String answerId : ansIds ) {
					userResponse = new UserResponse( new BigInteger(answerId), quesIdBig, recordIdBig );
					responseDao = new UserResponseDAO( mgr.getEntityManager());
					responseDao.addUserResponse(userResponse);
					mgr.closeTransaction();
					System.out.println("Type 4: " + answerId);
				}
			}
			
		}
		
		request.setAttribute("recordId", recordId);
		request.setAttribute("totalQuestions", QuizStartServlet.totalQuestions);
		request.setAttribute("index", newIndex);
		
		// If more questions left
		if( newIndex < QuizStartServlet.totalQuestions )
			request.getRequestDispatcher("/takeQuiz.jsp").forward(request, response);
		// If quiz completed
		else {
			// Get all user responses for the current record / attempt
			EntityManager emgr = mgr.getEntityManager();
			responseDao = new UserResponseDAO(emgr);
			List<UserResponse> responses = responseDao.getUserResponsesesByRecord(recordIdBig);
			
			
			ansDao = new AnswerChoiceDAO(emgr);
			quesDao = new QuestionDAO(emgr);
			
			// Calculate Score in %age
			int score = 0;
			List<AnswerChoice>answerschosen = new ArrayList<>();
			List<Question>quesAsked = new ArrayList<>();
			List<Integer>quesAnswered = new ArrayList<>();
			boolean exists = false;
			for( UserResponse userresponse : responses) {
				BigInteger answerId = userresponse.getAnsId();
				BigInteger questionId = userresponse.getQuesId();
				
				
				for( int i = 0; i < quesAnswered.size(); i++) {
					if(quesAnswered.get(i) == questionId.intValue())
						exists = true;
				}
				
				if(!exists) {
					quesAnswered.add(questionId.intValue());
					
					AnswerChoice answerchoice = ansDao.getAnswerChoice(answerId.longValue()); 
					Question questionasked = quesDao.getQuestion(questionId.longValue());
					if( answerchoice.getCorrect() == 1 && questionasked.getDifficultylevel() == 3 )
						score += 3;
					else if( answerchoice.getCorrect() == 1 && questionasked.getDifficultylevel() == 2 )
						score += 2;
					else if( answerchoice.getCorrect() == 1 && questionasked.getDifficultylevel() == 1 )
						score++;
					answerschosen.add(answerchoice);
					quesAsked.add(questionasked);
					System.out.println(questionasked.getQuestion()+"--"+answerchoice.getAnswer()+"--"+score);
				}
				exists = false;
					
			}
			int outOf = (QuizStartServlet.diffQuesNumber * 3) + (QuizStartServlet.medQuesNumber * 2) + (QuizStartServlet.easyQuesNumber);
			score = (score * 100) / outOf;
			
			// Complete and Update Record
			System.out.println("RECORDID(Merge): " + recordId);
			
			recordDao = new RecordDAO(emgr);
			recordDao.updateRecord( recordId, new Date(), score );
			emgr.getTransaction().commit();
			
			// Create Comment for the Attempt
			Comment comment = new Comment( recordIdBig );
			commentDao = new CommentDAO(emgr);
			comment = commentDao.addComment(comment);
			emgr.close();
			request.setAttribute("comment", comment);
			request.setAttribute("score", score);
			request.setAttribute("answerschosen", answerschosen);
			request.setAttribute("quesAsked", quesAsked);
			request.getRequestDispatcher("/completed.jsp").forward(request, response);
		}
	}

}




























