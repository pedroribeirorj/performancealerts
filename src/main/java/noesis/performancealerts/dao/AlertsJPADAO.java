package noesis.performancealerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import noesis.performancealerts.model.CasoDeTeste;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Test;
import utils.Constants;

public class AlertsJPADAO {
	private static AlertsJPADAO instance;
	protected EntityManager entityManager;

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
					ex.printStackTrace();
				entityManager.getTransaction().rollback();
			} catch (Exception e) {
				remove(alerts);
			}
		}
	}

	public void merge(Alerts Alerts, boolean debug) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(Alerts);
			if (!debug)
				entityManager.getTransaction().commit();
			else
				entityManager.getTransaction().rollback();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Alerts Alerts) {
		try {
			entityManager.getTransaction().begin();
			Alerts = entityManager.find(Alerts.class, Alerts.getId());
			entityManager.remove(Alerts);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Alerts Alerts = getById(id);
			remove(Alerts);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
