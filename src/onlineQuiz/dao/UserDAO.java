package onlineQuiz.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import onlineQuiz.model.User;

/**
 * Session Bean implementation class UserDAO
 */
@Stateless
@LocalBean
public class UserDAO {

	@PersistenceContext(unitName = "OnlineQuizWebApp")
	private EntityManager em;
    
    public void addUser( User user ) {
    	em.persist(user);
    }
    
    public void removeUser( int userId ) {
    	em.remove( getUser(userId) );
    }
    
    public void updateUser( User user ) {
    	em.merge(user);
    }
    
    public User getUserByEmail( String email ) {
    	return em.find(User.class, email);
    }
        
    public User getUser( int userId ) {
		return em.find(User.class, userId);
    	
    }
    
    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return em.createNamedQuery("User.findAll").getResultList();
    	
    }
    
    

}
