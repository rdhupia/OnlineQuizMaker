package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Question;

/**
 * Session Bean implementation class QuestionDAO
 */
@Stateless
public class QuestionDAO {

	private EntityManager em;

	public QuestionDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addQuestion( Question Question ) {
    	em.persist(Question);
    }
    
    public void removeQuestion( int QuestionId ) {
    	em.remove( getQuestion(QuestionId) );
    }
    
    public void updateQuestion( Question Question ) {
    	em.merge(Question);
    }
    
    @SuppressWarnings("unchecked")
	public List<Question> getQuestionsByQuiz( BigInteger quizId ) {
    	return em.createQuery("SELECT q FROM Question q where q.quizId =:value1").setParameter("value1", quizId).getResultList();
    }
        
    public Question getQuestion( int QuestionId ) {
		return em.find(Question.class, QuestionId);
    }
    
    @SuppressWarnings("unchecked")
	public List<Question> getAllQuestions() {
		return em.createNamedQuery("Question.findAll").getResultList();
    }


}
