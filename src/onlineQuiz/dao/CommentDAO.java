package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Comment;

/**
 * Session Bean implementation class CommentDAO
 */
@Stateless
public class CommentDAO {

	private EntityManager em;

	public CommentDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addComment( Comment Comment ) {
    	em.persist(Comment);
    }
    
    public void removeComment( int CommentId ) {
    	em.remove( getComment(CommentId) );
    }
    
    public void updateComment( Comment Comment ) {
    	em.merge(Comment);
    }
    
    @SuppressWarnings("unchecked")
	public List<Comment> getCommentsByRecord( BigInteger recordId ) {
    	return em.createQuery("SELECT c FROM Comment c where c.recordId =:value1").setParameter("value1", recordId).getResultList();
    }
        
    public Comment getComment( int CommentId ) {
		return em.find(Comment.class, CommentId);
    }
    
    @SuppressWarnings("unchecked")
	public List<Comment> getAllComments() {
		return em.createNamedQuery("Comment.findAll").getResultList();
    }

}
