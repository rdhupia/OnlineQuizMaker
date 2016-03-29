package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.AnswerChoiceDAO;
import onlineQuiz.dao.QuestionDAO;
import onlineQuiz.dao.QuizDAO;
import onlineQuiz.dao.RecordDAO;
import onlineQuiz.model.AnswerChoice;
import onlineQuiz.model.Question;
import onlineQuiz.model.Quiz;
import onlineQuiz.model.Record;

/**
 * Servlet implementation class QuizStartServlet
 */
@WebServlet(description = "Starts selected quiz", urlPatterns = { "/QuizStartServlet" })
public class QuizStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static int totalQuestions = 0;
    public static int currentQuesNumber = 0;   // Changes only when answer submitted
    public static int diffQuesNumber = 0;
    public static int easyQuesNumber = 0;
    public static int medQuesNumber = 0;
    public static long recordNumber = -1;
	
    private QuizDAO quizDao;
    private QuestionDAO quesDao;
    private RecordDAO recordDao;
 //   private QuestionTypeDAO quesTypeDao;
    private AnswerChoiceDAO ansDao;
 //   private UserDAO userDao;
 //   private UserResponseDAO responseDao;

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
		
		// Get parameters from quizStart.jsp
		long quizId = Integer.parseInt(request.getParameter("quizId"));
		BigInteger quizIdBig = new BigInteger( request.getParameter("quizId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		BigInteger userIdBig = new BigInteger( request.getParameter("userId"));
		System.out.println("QuizId: "+ quizId +"; UserId: "+ userId);
		
		// Get quiz
		Quiz quiz = quizDao.getQuiz(quizId);
		diffQuesNumber = quiz.getDifficultqs();
		medQuesNumber = quiz.getMediumqs();
		easyQuesNumber = quiz.getEasyqs();
		System.out.println("QuizStartServlet=> Diff: " +diffQuesNumber+ "; easy: "+ easyQuesNumber + "; med:  "+ medQuesNumber);
		mgr.closeTransaction();
		
		totalQuestions = quiz.getDifficultqs() + quiz.getEasyqs() + quiz.getMediumqs();
		System.out.println("QuizStartServlet=> TotalQs: " + totalQuestions);
		
		quesDao = new QuestionDAO( mgr.getEntityManager() );
		// Get all questions associated with quizId
		List<Question> questions = quesDao.getQuestionsByQuiz(quizIdBig);
		mgr.closeTransaction();
		
		ansDao = new AnswerChoiceDAO( mgr.getEntityManager() );
		List<List<AnswerChoice>> answers = new ArrayList<>();
		List<AnswerChoice> answerGroup = new ArrayList<>();
		
		// Shuffle questions
		Collections.shuffle(questions);
		
		// Set Questions for Quiz and get answer choices
		List<Question> quizQuestions = new ArrayList<>();
		int easy = 0;
		int med = 0;
		int diff = 0;
		boolean canAddToQuiz = false;
		
		for( int i = 0, x = 0; x < totalQuestions && i < questions.size(); i++ ) {
			if( questions.get(i).getDifficultylevel() == 1 && easy < easyQuesNumber ) {
				canAddToQuiz = true;
				easy++;
			}
			else if( questions.get(i).getDifficultylevel() == 2 && med < medQuesNumber ) {
				canAddToQuiz = true;
				med++;
			}
			else if( questions.get(i).getDifficultylevel() == 3 && diff < diffQuesNumber ) {
				canAddToQuiz = true;
				diff++;
			}
			if( canAddToQuiz ) {
				quizQuestions.add(questions.get(i));
				answerGroup = ansDao.getAnswerChoicesByQuestion(BigInteger.valueOf(questions.get(i).getQuesid()));
				answers.add(answerGroup);
				x++;
			}
			canAddToQuiz = false;
			if( easy == easyQuesNumber && med == medQuesNumber && diff == diffQuesNumber ) 
				break;
		}
		
		System.out.println("Question 4: " + quizQuestions.get(4).getQuestion() + "; Answer 1 for 4: " + answers.get(4).get(0).getAnswer());
		mgr.closeTransaction();
		
		// Create new Record for the user's New Quiz Attempt
		Record record = new Record(quizIdBig, 0, userIdBig);
		EntityManager em = mgr.getEntityManager();
		recordDao = new RecordDAO( em );
		recordNumber = recordDao.addRecordAndGetId(record);
		System.out.println("RECORDID(Create): " + recordNumber);
		System.out.println("RecordId: " + recordNumber);
		System.out.println("QuizStartServlet=> Answers: " + answers.size());
		for( int i =0; i < answers.size(); i++)
			System.out.println(answers.get(i).get(0).getAnswer());
		System.out.println("QuizStartServlet=> Questions: " + quizQuestions.size());
		
		request.setAttribute("recordId", recordNumber);
		request.setAttribute("totalQuestions", totalQuestions);
		request.setAttribute("questions", quizQuestions);
		request.setAttribute("answers", answers);
		request.setAttribute("index", currentQuesNumber);
		request.getRequestDispatcher("/takeQuiz.jsp").forward(request, response);
		
	}

}













