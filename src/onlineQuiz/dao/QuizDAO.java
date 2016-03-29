package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Quiz;

/**
 * Session Bean implementation class QuizDAO
 */
@Stateless
public class QuizDAO {
	
	private EntityManager em;

	public QuizDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addQuiz( Quiz Quiz ) {
    	em.persist(Quiz);
    }
    
    public void removeQuiz( int QuizId ) {
    	em.remove( getQuiz(QuizId) );
    }
    
    public void updateQuiz( Quiz Quiz ) {
    	em.merge(Quiz);
    }
    
    @SuppressWarnings("unchecked")
	public List<Quiz> getQuizzesBySubject( BigInteger subjectId ) {
    	return em.createQuery("SELECT q FROM Quiz q where q.subjectId =:value1").setParameter("value1", subjectId).getResultList();
    }
        
    public Quiz getQuiz( long quizId ) {
		return em.find(Quiz.class, quizId);
    }
    
    @SuppressWarnings("unchecked")
	public List<Quiz> getAllQuizzes() {
		return em.createNamedQuery("Quiz.findAll").getResultList();
    }


}
