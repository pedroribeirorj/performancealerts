package noesis.performancealerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import noesis.performancealerts.model.CasoDeTeste;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Test;
import noesis.performancealerts.model.Test;
import utils.Constants;

public class TestJPADAO {
	private static TestJPADAO instance;
	protected EntityManager entityManager;

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
			e.printStackTrace();
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

	public void persist(Test Test) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(Test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Test Test) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Test Test) {
		try {
			entityManager.getTransaction().begin();
			Test = entityManager.find(Test.class, Test.getId());
			entityManager.remove(Test);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Test Test = getById(id);
			remove(Test);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
