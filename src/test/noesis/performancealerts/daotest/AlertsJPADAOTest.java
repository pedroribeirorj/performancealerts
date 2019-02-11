package noesis.performancealerts.daotest;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.model.Alerts;
import utils.Constants;

public class AlertsJPADAOTest {

	static Alerts alert;
	static AlertsJPADAO dao;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
//		dao.remove(dao.getById(-1));
//		dao.remove(alert);
	}

	@Before
	public void setUp() {
		alert = new Alerts();
		alert.setData(String.valueOf(new Timestamp(System.currentTimeMillis())));
		alert.setId(-1);
		alert.setIdRun(1);
		alert.setIdTest(1);
		alert.setSeverity(String.valueOf(Constants.GRAVIDADE_VIOLACAO_CRITICA));
		alert.setTypeError(String.valueOf(Constants.VIOLACAO_POR_INDISPONIBILIDADE));
		alert.setValue("0");
		alert.setId(-1);
		dao = AlertsJPADAO.getInstance();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void persist() {
		try {
			dao.persist(alert);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void find() {
		Alerts r = dao.getById(1);
		assertNotEquals(null, r);
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
			dao.merge(alert, true);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}
}
