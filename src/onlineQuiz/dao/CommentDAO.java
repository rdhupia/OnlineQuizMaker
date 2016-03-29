package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Comment;
import onlineQuiz.model.Record;

/**
 * Session Bean implementation class CommentDAO
 */
@Stateless
public class CommentDAO {

	private EntityManager em;

	public CommentDAO(EntityManager em) {
		this.em = em;
	}
	
    public Comment addComment( Comment comment ) {
    	String text = "Did you even take the Quiz?";
    	Record r = (Record)em.find(Record.class, comment.getRecordId().longValue());
    	if( r != null ) {
    		int score = r.getScore();
    		if( score > 0 && score < 35 ) 
    			text = "You can certainly do better, brush up on the topics and try again later.";
    		else if ( score >= 35 && score < 55 )
    			text = "Good Effort, but there's room for improvement";
    		else if ( score >= 55 && score < 75 )
    			text = "You know your concepts well, well done!!";
    		else if ( score >= 75 && score < 90 )
    			text = "Great Job, you've done very well indeed!!";
    		else if ( score >= 90 && score < 99 )
    			text = "You're a freakin Genius!!!";
    		else if ( score >= 100 )
    			text = "A Perfect Performance, Mind Blowing!!";
    	}
    	
    	Comment newComment = new Comment(comment.getRecordId(), text);
    	em.persist(newComment);
    	return newComment;
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
