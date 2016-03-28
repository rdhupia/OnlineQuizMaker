package onlineQuiz.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Subject;

/**
 * Session Bean implementation class SubjectDAO
 */
@Stateless
public class SubjectDAO {

	private EntityManager em;
    /**
     * Default constructor. 
     */
    public SubjectDAO(EntityManager em) {
    	this.em = em;
    }
	
    public void addSubject( Subject subject ) {
    	em.persist(subject);
    }
    
    public void removeSubject( int subjectId ) {
    	em.remove( getSubject(subjectId) );
    }
    
    public void updateSubject( Subject subject ) {
    	em.merge(subject);
    }
    
    public Subject getSubject( int subjectId ) {
		return em.find(Subject.class, subjectId);
    }
    
    @SuppressWarnings("unchecked")
	public List<Subject> getAllSubjects() {
		return em.createNamedQuery("Subject.findAll").getResultList();
    }
}
