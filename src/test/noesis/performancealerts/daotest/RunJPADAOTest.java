package noesis.performancealerts.daotest;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.model.Run;

public class RunJPADAOTest {
	static Run run;
	static RunJPADAO dao;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		dao.remove(dao.getById(-1));
	}

	@Before
	public void setUp() {
		run = new Run();
		run.setData(new Timestamp(System.currentTimeMillis()));
		run.setIdRun(-1);
		run.setCycleId("0");
		run.setVersionId("0");
		run.setProjectId("0");
		run.setFkAappType(0);
		run.setFkRepositoryServersId(0);
		dao = RunJPADAO.getInstance();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void persist() {
		try {
			dao.persist(run);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void findAll() {
		try {
			dao.findAll();
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void merge() {
		try {
			dao.merge(run);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void find() {
		Run r = dao.getAnyRun();
		assertNotEquals(null, r);
	}

}