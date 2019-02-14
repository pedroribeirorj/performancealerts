package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.model.Run;
import noesis.performancealerts.utils.Constants;

public class RunJPADAO {

	private static RunJPADAO instance;
	protected EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(RunJPADAO.class.getName());
	
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

	public Run getAnyRun() {
		try {
			Query q = entityManager.createQuery("FROM Run r");
			q.setFirstResult(0);
			List query = q.getResultList();
			return (Run) query.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Run> findAll() {
		return entityManager.createQuery("FROM " + Run.class.getName()).getResultList();
	}

	public void persist(Run run) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (!Constants.TST_MODE)
					logger.error(ex.getMessage());
				entityManager.getTransaction().rollback();
			} catch (Exception e) {
				remove(run);
			}
		}
	}

	public void merge(Run run) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Run run) {
		try {
			if (run == null)
				return;
			entityManager.getTransaction().begin();
			run = entityManager.find(Run.class, run.getIdRun());
			entityManager.remove(run);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			if (!(Constants.TST_MODE && ex.getClass() == javax.persistence.RollbackException.class)) {
				logger.error(ex.getMessage());
				entityManager.getTransaction().rollback();
			}
		}
	}

	public void rollBack() {
		if (!entityManager.getTransaction().getRollbackOnly())
			entityManager.getTransaction().rollback();
	}

	public void removeById(final int id) {
		try {
			Run run = getById(id);
			remove(run);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

}
