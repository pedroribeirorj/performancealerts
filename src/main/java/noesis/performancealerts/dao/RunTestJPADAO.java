package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.model.RunTest;
import utils.Constants;

public class RunTestJPADAO {
	private static RunTestJPADAO instance;
	protected EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(RunTestJPADAO.class.getName());
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

	public List<Integer> findTestsByRunID(int idRun) {
		Query q = entityManager
				.createQuery(Constants.QUERY_FIND_TESTS_BY_RUN_ID);
		q.setParameter("idRun", idRun);
		return q.getResultList();
	}

	public void persist(RunTest runTest) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(runTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(RunTest runTest) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(runTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(RunTest runTest) {
		try {
			entityManager.getTransaction().begin();
			runTest = entityManager.find(RunTest.class, runTest.getId());
			entityManager.remove(runTest);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			RunTest runTest = getById(id);
			remove(runTest);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}
