package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import noesis.performancealerts.model.RunTest;
import utils.Constants;

public class RunTestJPADAO {
	private static RunTestJPADAO instance;
	protected EntityManager entityManager;

	public static RunTestJPADAO getInstance() {
		if (instance == null) {
			instance = new RunTestJPADAO();
		}

		return instance;
	}

	private RunTestJPADAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public RunTest getById(final int id) {
		return entityManager.find(RunTest.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RunTest> findAll() {
		return entityManager.createQuery("FROM " + RunTest.class.getName()).getResultList();
	}

	public List<Integer> findTestsByRunID(int id_run) {
		Query q = entityManager
				.createQuery(Constants.QUERY_FIND_TESTS_BY_RUN_ID);
		q.setParameter("idRun", id_run);
		List l = q.getResultList();
		return l;
	}

	public void persist(RunTest RunTest) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(RunTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(RunTest RunTest) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(RunTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(RunTest RunTest) {
		try {
			entityManager.getTransaction().begin();
			RunTest = entityManager.find(RunTest.class, RunTest.getId());
			entityManager.remove(RunTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			RunTest RunTest = getById(id);
			remove(RunTest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
