package noesis.performancealerts.daotest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.RunTest;

public class RunTestJPADAOTest {

	RunTest runTest;
	RunTestJPADAO dao;
	public static boolean persistido;

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {

	}

	@Before
	public void setUp() {
		runTest = new RunTest();
		runTest.setIdRun(RunJPADAO.getInstance().getAnyRun().getIdRun());
		runTest.setIdTest(TestJPADAO.getInstance().getAnyTest().getId());
		runTest.setStatus(1);
		dao = RunTestJPADAO.getInstance();
		persistido = false;
	}

	@After
	public void tearDown() {
		if (persistido) {
			dao.removeById(dao.findLastRunTest().getId());
			persistido = false;
		}
	}

	@Test
	public void persist() {
		try {
			if (runTest != null) {
				dao.persist(runTest);
				persistido = true;
			}
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
			dao.merge(runTest);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void find() {
		RunTest r = dao.getById(runTest.getId());
		assertNotEquals(null, r);
	}

}
