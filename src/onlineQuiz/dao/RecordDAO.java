package onlineQuiz.dao;

import java.math.BigInteger;
import java.util.Date;
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
    	em.persist(Record);
    }
    
    public void removeRecord( int RecordId ) {
    	em.remove( getRecord(RecordId) );
    }
    
    public void updateRecord( long recordid, Date dateofquiz, int score ) {
    	Record r = (Record)em.find(Record.class, recordid);
    	if(r != null) {
    		r.setDateofquiz(dateofquiz);
    		r.setScore(score);
    		System.out.println("Record updated successfully");
    	}
    	else
    		System.out.println("Entity does not exist");
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
    
    public long addRecordAndGetId( Record record) {
    	em.persist(record);
    	em.getTransaction().commit();
    	return record.getRecordid();
    }
    
    public long updateRecordAndGetId( Record record) {
    	em.merge(record);
    	em.getTransaction().commit();
    	return record.getRecordid();
    }
    
    @SuppressWarnings("unchecked")
	public List<Record> getAllRecords() {
		return em.createNamedQuery("Record.findAll").getResultList();
    }


}
