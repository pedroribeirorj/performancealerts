package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.model.Test;

public class TestJPADAO {
	private static TestJPADAO instance;
	protected EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(TestJPADAO.class.getName());

	public static TestJPADAO getInstance() {
		if (instance == null) {
			instance = new TestJPADAO();
		}

		return instance;
	}

	private TestJPADAO() {
		entityManager = getEntityManager();
	}

	public Test getAnyTest() {
		try {
			Query q = entityManager.createQuery("FROM Test t");
			q.setFirstResult(0);
			List query = q.getResultList();
			return (Test) query.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Test getById(final int id) {
		return entityManager.find(Test.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Test> findAll() {
		return entityManager.createQuery("FROM " + Test.class.getName()).getResultList();
	}

	public void persist(Test test) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Test test) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Test test) {
		try {
			entityManager.getTransaction().begin();
			test = entityManager.find(Test.class, test.getId());
			entityManager.remove(test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Test test = getById(id);
			remove(test);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

}
