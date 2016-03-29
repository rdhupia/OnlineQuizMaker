package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import onlineQuiz.model.Record;

/**
 * Session Bean implementation class RecordDAO
 */
@Stateless
public class RecordDAO {

	private EntityManager em;

	public RecordDAO(EntityManager em) {
		this.em = em;
	}
	
    public void addRecord( Record Record ) {
    	em.persist(Record); System.out.println("PERSIS RECORD");
    }
    
    public void removeRecord( int RecordId ) {
    	em.remove( getRecord(RecordId) );
    }
    
    public void updateRecord( Record Record ) {
    	em.merge(Record);
    }
    
    @SuppressWarnings("unchecked")
	public List<Record> getRecordsByQuiz( BigInteger quizId ) {
    	return em.createQuery("SELECT r FROM Record r where r.quizId =:value1").setParameter("value1", quizId).getResultList();
    }

    @SuppressWarnings("unchecked")
	public List<Record> getRecordsByUser( BigInteger userId ) {
    	return em.createQuery("SELECT r FROM Record r where r.userId =:value1").setParameter("value1", userId).getResultList();
    }    
    
	public Record getRecordByUserAndQuiz( BigInteger userId, BigInteger quizId ) {
    	return (Record) em.createQuery("SELECT r FROM Record r where r.userId =:value1 and r.quizId =:value2").setParameter("value1", userId).setParameter("value2", quizId).getSingleResult();
    }
    
    public Record getRecord( int RecordId ) {
		return em.find(Record.class, RecordId);
    }
    
    @SuppressWarnings("unchecked")
	public List<Record> getAllRecords() {
		return em.createNamedQuery("Record.findAll").getResultList();
    }


}
