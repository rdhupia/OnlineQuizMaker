package onlineQuiz.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.QuestionType;

/**
 * Session Bean implementation class QuestionTypeDAO
 */
@Stateless
public class QuestionTypeDAO {

	private EntityManager em;

	public QuestionTypeDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addQuestionType( QuestionType QuestionType ) {
    	em.persist(QuestionType);
    }
    
    public void removeQuestionType( int QuestionTypeId ) {
    	em.remove( getQuestionType(QuestionTypeId) );
    }
    
    public void updateQuestionType( QuestionType QuestionType ) {
    	em.merge(QuestionType);
    }
            
    public QuestionType getQuestionType( int QuestionTypeId ) {
		return em.find(QuestionType.class, QuestionTypeId);
    }
    
    @SuppressWarnings("unchecked")
	public List<QuestionType> getAllQuestionTypes() {
		return em.createNamedQuery("QuestionType.findAll").getResultList();
    }


}
