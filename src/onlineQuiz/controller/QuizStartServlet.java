package onlineQuiz.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineQuiz.dao.QuizDAO;

/**
 * Servlet implementation class QuizStartServlet
 */
@WebServlet(description = "Starts selected quiz", urlPatterns = { "/QuizStartServlet" })
public class QuizStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private QuizDAO quizDao;/*
    private QuestionDAO quesDao;
    private QuestionTypeDAO quesTypeDao;
    private AnswerChoiceDAO ansDAO;
    private UserDAO userDAO;
    private UserResponseDAO responseDAO;*/

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
	//	quizDao = mgr.getEntityManager();
		
	}

}
