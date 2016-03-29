package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import onlineQuiz.model.UserResponse;

/**
 * Session Bean implementation class UserResponseDAO
 */
@Stateless
public class UserResponseDAO {

	private EntityManager em;

	public UserResponseDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addUserResponse( UserResponse UserResponse ) {
    	em.persist(UserResponse);
    }
    
    public void removeUserResponse( int UserResponseId ) {
    	em.remove( getUserResponse(UserResponseId) );
    }
    
    public void updateUserResponse( UserResponse UserResponse ) {
    	em.merge(UserResponse);
    }
    
    @SuppressWarnings("unchecked")
	public List<UserResponse> getUserResponsesesByRecord( BigInteger recordId ) {
    	return em.createQuery("SELECT u FROM UserResponse u where u.recordId =:value1").setParameter("value1", recordId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<UserResponse> getUserResponsesesByQuestion( BigInteger quesId ) {
    	return em.createQuery("SELECT u FROM UserResponse u where u.quesId =:value1").setParameter("value1", quesId).getResultList();
    }
        
    public UserResponse getUserResponse( int UserResponseId ) {
		return em.find(UserResponse.class, UserResponseId);
    }
    
    @SuppressWarnings("unchecked")
	public List<UserResponse> getAllUserResponses() {
		return em.createNamedQuery("UserResponse.findAll").getResultList();
    }

}
