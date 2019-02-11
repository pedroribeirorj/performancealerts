package noesis.performancealerts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.model.Alerts;
import utils.Constants;

public class AlertsJPADAO {
	private static AlertsJPADAO instance;
	protected EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(AlertsJPADAO.class.getName());

	public static AlertsJPADAO getInstance() {
		if (instance == null) {
			instance = new AlertsJPADAO();
		}

		return instance;
	}

	private AlertsJPADAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Alerts getById(final int id) {
		return entityManager.find(Alerts.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Alerts> findAll() {
		return entityManager.createQuery("FROM " + Alerts.class.getName()).getResultList();
	}

	public void persist(Alerts alerts) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(alerts);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (!Constants.TST_MODE)
					logger.error(ex.getMessage());
				entityManager.getTransaction().rollback();
			} catch (Exception e) {
				remove(alerts);
			}
		}
	}

	public void merge(Alerts alerts, boolean debug) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(alerts);
			if (!debug)
				entityManager.getTransaction().commit();
			else
				entityManager.getTransaction().rollback();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Alerts alerts) {
		try {
			entityManager.getTransaction().begin();
			alerts = entityManager.find(Alerts.class, alerts.getId());
			entityManager.remove(alerts);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Alerts alerts = getById(id);
			remove(alerts);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}
