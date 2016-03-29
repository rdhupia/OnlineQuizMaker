package onlineQuiz.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import onlineQuiz.dao.UserResponseDAO;
import onlineQuiz.model.AnswerChoice;
import onlineQuiz.model.UserResponse;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserResponseDAO responseDao;
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
		EntityManager em = mgr.getEntityManager();
		responseDao = new UserResponseDAO( em );

		// Get parameters from takeQuiz.jsp
		int quesType = Integer.parseInt(request.getParameter("typeOfQ"));
		int newIndex = Integer.parseInt(request.getParameter("newIndex"));
		int recordId = Integer.parseInt(request.getParameter("recordId"));
		BigInteger recordIdBig = new BigInteger(request.getParameter("recordId"));
		BigInteger quesIdBig = new BigInteger( request.getParameter("quesId"));
		System.out.println("New Index: " + newIndex);
		
		HttpSession session = request.getSession();
		List<List<AnswerChoice>> answers = (List<List<AnswerChoice>>) session.getAttribute("quizAnswers");
		
		System.out.println("AnswersList: " + answers.size());
		List<AnswerChoice> answerGroup = answers.get(newIndex-1);
		
		// Get Answers given
		int ansId = 0;
		UserResponse userResponse;
		if( quesType == 1 || quesType ==2) {
			if( request.getParameter("ansId") != null )
				ansId = Integer.parseInt(request.getParameter("ansId"));
			userResponse = new UserResponse( BigInteger.valueOf(ansId), quesIdBig, recordIdBig );
			responseDao.addUserResponse(userResponse);
			mgr.closeTransaction();
			System.out.println("Type 1/2: " + ansId);
		}
		else if( quesType == 3 ) {
			if(answerGroup.get(0).getAnswer().equalsIgnoreCase(request.getParameter("ans"))) {
				ansId = (int) answerGroup.get(0).getAnsid();
				System.out.println("Correct Ans: " + answerGroup.get(0).getAnswer());
			}
			else
				ansId = 93;	
			userResponse = new UserResponse( BigInteger.valueOf(ansId), quesIdBig, recordIdBig );
			responseDao.addUserResponse(userResponse);
			mgr.closeTransaction();
			System.out.println("Type 3: " + ansId);
		}
		else if( quesType == 4 ) {
			String[] ansIds = request.getParameterValues("ansIds");
			for( String answerId : ansIds ) {
				userResponse = new UserResponse( new BigInteger(answerId), quesIdBig, recordIdBig );
				responseDao.addUserResponse(userResponse);
				em.getTransaction().commit();
				System.out.println("Type 4: " + answerId);
			}
			em.close();
		}
		
		request.setAttribute("recordId", recordId);
		request.setAttribute("totalQuestions", QuizStartServlet.totalQuestions);
		request.setAttribute("index", newIndex);
		request.getRequestDispatcher("/takeQuiz.jsp").forward(request, response);

	}

}
