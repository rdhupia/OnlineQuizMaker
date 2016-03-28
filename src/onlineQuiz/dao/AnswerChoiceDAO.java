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
    
    public void removeAnswerChoice( int AnswerChoiceId ) {
    	em.remove( getAnswerChoice(AnswerChoiceId) );
    }
    
    public void updateAnswerChoice( AnswerChoice AnswerChoice ) {
    	em.merge(AnswerChoice);
    }
    
    @SuppressWarnings("unchecked")
	public List<AnswerChoice> getAnswerChoicesByQuestion( BigInteger quesId ) {
    	return em.createQuery("SELECT a FROM AnswerChoice a where a.quesId =:value1").setParameter("value1", quesId).getResultList();
    }
        
    public AnswerChoice getAnswerChoice( int AnswerChoiceId ) {
		return em.find(AnswerChoice.class, AnswerChoiceId);
    }
    
    @SuppressWarnings("unchecked")
	public List<AnswerChoice> getAllAnswerChoices() {
		return em.createNamedQuery("AnswerChoice.findAll").getResultList();
    }


}
