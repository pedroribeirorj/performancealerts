package noesis.performancealerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;

import noesis.performancealerts.model.CasoDeTeste;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.model.Task;
import noesis.performancealerts.model.TaskLog;
import noesis.performancealerts.model.Test;
import utils.Constants;

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

	public Run getAnyRun() {
		try {
			Query q = entityManager.createQuery("FROM Run r");
			q.setFirstResult(0);
			List query = q.getResultList();
			return (Run) query.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	public CasoDeTeste montaCasodeTeste(Object[] vetObj) {
		Run r = (Run) vetObj[0];
		RunTest rt = (RunTest) vetObj[1];
		Test t = (Test) vetObj[2];

		CasoDeTeste j = new CasoDeTeste();
		j.setRun(r);
		j.setRunTest(rt);
		j.setTest(t);
		return j;
	}

	public List<CasoDeTeste> recuperaCasosDeTestePorSuite(String suiteId, int idCasoDeTeste) {
		// recupera as 100 jornadas mais recentes de uma suíte

		Query q = entityManager
				.createQuery(Constants.RECUPERA_JORNADAS_POR_SUITES + " limit " + Constants.VOLUME_AMOSTRAL);
		q.setParameter(1, suiteId);
		q.setParameter(2, idCasoDeTeste);
		List query = q.getResultList();

		List<CasoDeTeste> casoDeTestes = new ArrayList<CasoDeTeste>();

		for (Iterator iterator = query.iterator(); iterator.hasNext();) {
			// Query contém os registros da consulta
			// Cada objeto é uma linha, que será dividida em classes
			Object[] object = (Object[]) iterator.next();
			CasoDeTeste j = montaCasodeTeste(object);
			casoDeTestes.add(j);
		}
		return casoDeTestes;

	}
}
