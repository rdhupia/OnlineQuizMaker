package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.AnswerChoice;

/**
 * Session Bean implementation class AnswerChoiceDAO
 */
@Stateless
public class AnswerChoiceDAO {

	private EntityManager em;

	public AnswerChoiceDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addAnswerChoice( AnswerChoice AnswerChoice ) {
    	em.persist(AnswerChoice);
    }
    
    public void removeAnswerChoice( long ansId ) {
    	em.remove( getAnswerChoice(ansId) );
    }
    
    public void updateAnswerChoice( AnswerChoice AnswerChoice ) {
    	em.merge(AnswerChoice);
    }
    
    public void updateAnswer( String text, long id ) {
    	AnswerChoice a = (AnswerChoice)em.find(AnswerChoice.class, id);
    	if( a != null ) {
    		a.setAnswer(text);
    		System.out.println("Answer Updated");
    	}
    	else
    		System.out.println("Answer entity not found");
    }
    

    public void updateAnswerWithCorrectness( String text, long id, int correct ) {
    	AnswerChoice a = (AnswerChoice)em.find(AnswerChoice.class, id);
    	if( a != null ) {
    		a.setAnswer(text);
    		a.setCorrect(correct);
    		System.out.println("Answer Updated");
    	}
    	else
    		System.out.println("Answer entity not found");
    }
    
    @SuppressWarnings("unchecked")
	public List<AnswerChoice> getAnswerChoicesByQuestion( BigInteger quesId ) {
    	return em.createQuery("SELECT a FROM AnswerChoice a where a.quesId =:value1").setParameter("value1", quesId).getResultList();
    }
        
    public AnswerChoice getAnswerChoice( long l ) {
		return em.find(AnswerChoice.class, l);
    }
    
    @SuppressWarnings("unchecked")
	public List<AnswerChoice> getAllAnswerChoices() {
		return em.createNamedQuery("AnswerChoice.findAll").getResultList();
    }


}
