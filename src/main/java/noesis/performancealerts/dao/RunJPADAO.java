package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RunJPADAO {

	private static RunJPADAO instance;
	protected EntityManager entityManager;

	public static RunJPADAO getInstance() {
		if (instance == null) {
			instance = new RunJPADAO();
		}

		return instance;
	}

	private RunJPADAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Run getById(final int id) {
		return entityManager.find(Run.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Run> findAll() {
		return entityManager.createQuery("FROM " + Run.class.getName()).getResultList();
	}

	public void persist(Run Run) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Run Run) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Run Run) {
		try {
			entityManager.getTransaction().begin();
			Run = entityManager.find(Run.class, Run.getId_run());
			entityManager.remove(Run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Run Run = getById(id);
			remove(Run);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
