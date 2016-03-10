package edu.src.main.DAO;

import edu.src.main.MODEL.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;




public class UserDao {
	EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("HBU");
	EntityManager em = factory.createEntityManager();

	public void insertUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}
	
	public User findUser(int userId) {
		
		User user =null;

		user = em.find(User.class, userId);

		return user;

	}
	public User findUserbyName(String name) {
		List<User> users = new ArrayList<User>();
        User user=new User();

		Query query = em.createQuery("select user from User user where user.username=?1");
       query.setParameter(1, name);
		users = (List<User>) query.getResultList();
for(User u:users)
       user=u;
	return user;
	}
	public List<User> findAllUsers() {
		List<User> users = new ArrayList<User>();


		Query query = em.createQuery("select user from User user");
		users = (List<User>) query.getResultList();


		return users;
	}
	
	public List<User> updateUser(int userId, User user) {
		List<User> users = new ArrayList<User>();
		em.getTransaction().begin();
		
		user.setuId(userId);
		em.merge(user);
		Query query = em.createQuery("select user from User user");
		users = (List<User>) query.getResultList();

		em.getTransaction().commit();
		return users;
	}
	
	public List<User> removeUser(int userId){
		List<User> users = new ArrayList<User>();
		User user = null;

		em.getTransaction().begin();
		
		user = em.find(User.class, userId);
		em.remove(user);
		
		Query query = em.createQuery("select user from User user");
		users = (List<User>) query.getResultList();
		
		em.getTransaction().commit();
		
		return users;
	}
	

	public List<User> judgeUserPassword(String userName, String userPassword) {
		Query query = em
				.createQuery("select user from User user where user.username=?1 and user.password=?2");
		query.setParameter(1, userName);
		query.setParameter(2, userPassword);
		return (List<User>) query.getResultList();
	}
	
	
	
}