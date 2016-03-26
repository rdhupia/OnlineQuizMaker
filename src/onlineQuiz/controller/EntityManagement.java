package onlineQuiz.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagement {
	
	private static final String PERSISTENCE_UNIT_NAME = "OnlineQuizWebApp";
	private static EntityManagerFactory factory;
	private EntityManager em;
	
	public EntityManagement() {
		// No need to instantiate factory if already instantiated
		if(factory == null)
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	public EntityManager getEntityManager() {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		return em;
	}
	
	public void closeTransaction() {
		em.getTransaction().commit();
		em.close();
	}
	
	public void closeFactory() {
		factory.close();
	}
	
}
