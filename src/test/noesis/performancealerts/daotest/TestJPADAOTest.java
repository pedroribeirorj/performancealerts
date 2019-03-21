package noesis.performancealerts.daotest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.utils.Constants;
import java.sql.Timestamp;

public class TestJPADAOTest {

	noesis.performancealerts.model.Test test;
	static TestJPADAO dao;

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
		dao.remove(dao.getById(-1));
	}

	@Before
	public void setUp() {

		test = new noesis.performancealerts.model.Test();

		test.setCreated_date(new Timestamp(System.currentTimeMillis()));
		test.setDescription("TESTE");
		test.setFk_app_type_id(1);
		test.setFk_repository_type_id(1);
		test.setName_test("TESTE");
		test.setProject_id(String.valueOf(Constants.REPOSITORY_TYPE_TFS));
		test.setTest_case_id("1");
		test.setTest_cycle_id("1");
		test.setTest_version_id("1");
		test.setUpdated_date(new Timestamp(System.currentTimeMillis()));
		test.setUsername("TESTER");
		test.setId(-1);

		dao = TestJPADAO.getInstance();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void persist() {
		try {
			dao.persist(test);
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
			dao.merge(test);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void find() {
		noesis.performancealerts.model.Test r = dao.getById(test.getId());
		assertNotEquals(null, r);
	}

	@Test
	public void remove() {
		test = new noesis.performancealerts.model.Test();
		test.setCreated_date(new Timestamp(System.currentTimeMillis()));
		test.setDescription("TESTE");
		test.setFk_app_type_id(1);
		test.setFk_repository_type_id(1);
		test.setName_test("TESTE");
		test.setProject_id(String.valueOf(Constants.REPOSITORY_TYPE_TFS));
		test.setTest_case_id("1");
		test.setTest_cycle_id("1");
		test.setTest_version_id("1");
		test.setUpdated_date(new Timestamp(System.currentTimeMillis()));
		test.setUsername("TESTER");
		test.setId(-1);
		dao = TestJPADAO.getInstance();
		dao.persist(test);
		dao.remove(test);
		assert (true);
	}

	@Test
	public void removeById() {
		test = new noesis.performancealerts.model.Test();
		test.setCreated_date(new Timestamp(System.currentTimeMillis()));
		test.setDescription("TESTE");
		test.setFk_app_type_id(1);
		test.setFk_repository_type_id(1);
		test.setName_test("TESTE");
		test.setProject_id(String.valueOf(Constants.REPOSITORY_TYPE_TFS));
		test.setTest_case_id("1");
		test.setTest_cycle_id("1");
		test.setTest_version_id("1");
		test.setUpdated_date(new Timestamp(System.currentTimeMillis()));
		test.setUsername("TESTER");
		test.setId(-1);
		dao = TestJPADAO.getInstance();
		dao.persist(test);
		dao.removeById(test.getId());
		assert (true);
	}
}
