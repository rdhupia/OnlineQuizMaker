package onlineQuiz.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import onlineQuiz.model.User;
import onlineQuiz.security.Security;

public class EntityManagerClass {
	
	private static final String PERSISTENCE_UNIT_NAME = "OnlineQuizWebApp";
	private static EntityManagerFactory factory;
	
	public static void main(String[] args) {
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		// Start a new local transaction 
		em.getTransaction().begin();
		
		// Read existing entries in User
		User user = new User();
		//Security sec = new Security();
		user.setEmail("vito@myseneca.ca");
		user.setFirstname("BigVito");
		user.setLastname("Thegreat");
		user.setRole(4);
		user.setPassword("learner");
		em.persist(user);
		
		em.getTransaction().commit();
		
		User user2 = em.find(User.class, 4);
		System.out.println(user2.getFirstname());
		System.out.println(user2.getLastname());
		System.out.println(user2.getEmail());
		System.out.println(user2.getRole());
		System.out.println(user2.getPassword());
		
	}

}
