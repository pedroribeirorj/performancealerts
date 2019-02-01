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

import noesis.performancealerts.model.Jornada;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.model.Task;
import noesis.performancealerts.model.TaskLog;
import noesis.performancealerts.model.Test;

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

	public Jornada montaJornada(Object[] vetObj) {
		Run r = (Run) vetObj[0];
		RunTest rt = (RunTest) vetObj[1];
		Test t = (Test) vetObj[2];
		
		Jornada j = new Jornada();
		j.setRun(r);
		j.setRunTest(rt);
		j.setTest(t);
		return j;
	}

	public List<Jornada> recuperaJornadasPorSuite(String suiteId) {
		Query q = entityManager.createQuery(utils.Constants.RECUPERA_JORNADAS_POR_SUITES);
		q.setParameter("test_cycle_id", suiteId);
		List query = q.getResultList();

		List<Jornada> jornadas = new ArrayList<Jornada>();

		for (Iterator iterator = query.iterator(); iterator.hasNext();) {
			// Query contém os registros da consulta
			// Cada objeto é uma linha, que será dividida em classes
			Object[] object = (Object[]) iterator.next();
			Jornada j = montaJornada(object);
			jornadas.add(j);
		}
		return jornadas;

//		Gson g = new Gson();
//		String objetoToJson = g.toJson(r);
//		String objetoToJson1 = g.toJson(rt);
//		String objetoToJson2 = g.toJson(t);
//		String objetoToJson3 = g.toJson(task);
//		String objetoToJson4 = g.toJson(tl);
//		
//		System.out.println(objetoToJson);
//		System.out.println(objetoToJson1);
//		System.out.println(objetoToJson2);
//		System.out.println(objetoToJson3);
//		System.out.println(objetoToJson4);

	}
}
