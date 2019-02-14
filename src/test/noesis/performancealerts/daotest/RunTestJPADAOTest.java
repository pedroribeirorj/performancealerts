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
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;

public class RunTestJPADAOTest {

	static RunTest runTest;
	static RunTestJPADAO dao;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		dao.remove(dao.getById(-1));
	}

	@Before
	public void setUp() {
		runTest = new RunTest();
		runTest.setId(-1);
		runTest.setIdRun(-1);
		runTest.setIdTest(-1);
		runTest.setStatus(1);
		dao = RunTestJPADAO.getInstance();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void persist() {
		try {
			if (runTest != null)
				dao.persist(runTest);
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
