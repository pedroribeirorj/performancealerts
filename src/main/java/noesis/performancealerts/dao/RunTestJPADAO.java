package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.utils.Constants;
import noesis.performancealerts.utils.Utils;

public class RunTestJPADAO {
	private static RunTestJPADAO instance;
	protected EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(RunTestJPADAO.class.getName());
	static String fieldIdTest = "idTest";

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

	public List<RunTest> findByRunAndTest(int idRun, int idTest) {
		Query q = entityManager
				.createQuery("FROM " + RunTest.class.getName() + " where idRun = :idRun and idTest=:idTest");
		q.setParameter("idRun", idRun);
		q.setParameter(fieldIdTest, idTest);
		return q.getResultList();
	}

	public List<RunTest> findByRunsAndTest(List<Integer> idsRun, int idTest) {
		String sql = "FROM " + RunTest.class.getName() + " where idRun in "+Utils.getListaString(idsRun)+" and idTest=:idTest order by id desc";
		Query q = entityManager
				.createQuery(sql);
		q.setParameter(fieldIdTest, idTest);
		return q.getResultList();
	}

	public RunTest findLastRunTest() {
		Query q = entityManager.createQuery("FROM " + RunTest.class.getName() + " order by id desc");
		return (RunTest) q.getResultList().get(0);
	}

	public List<Integer> findTestsByRunID(int idRun) {
		Query q = entityManager.createQuery(Constants.QUERY_FIND_TESTS_BY_RUN_ID);
		q.setParameter("idRun", idRun);
		return q.getResultList();
	}

	public List<Integer> findLastsRunsByTestID(int idTest) {
		Query q = entityManager.createQuery(Constants.QUERY_FIND_TESTS_BY_LASTS_RUNS_BY_RUN_ID);
		q.setParameter(fieldIdTest, idTest);
		q.setMaxResults(Constants.VOLUME_AMOSTRAL);
		return q.getResultList();
	}

	public List<Integer> findRunsByTestID(int idTest) {
		Query q = entityManager.createQuery(Constants.QUERY_FIND_TESTS_BY_RUN_ID);
		q.setParameter(fieldIdTest, idTest);
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
