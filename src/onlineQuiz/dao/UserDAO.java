package onlineQuiz.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.User;

/**
 * Session Bean implementation class UserDAO
 */
@Stateless
public class UserDAO {
 
	private EntityManager em;
    
	public UserDAO(EntityManager em) {
		this.em = em;
	}
	
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
    	return (User) em.createQuery("SELECT u FROM User u where u.email =:value1").setParameter("value1", email).getSingleResult();
    }
        
    public User getUser( int userId ) {
		return em.find(User.class, userId);
    }
    
    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return em.createNamedQuery("User.findAll").getResultList();
    }

}
