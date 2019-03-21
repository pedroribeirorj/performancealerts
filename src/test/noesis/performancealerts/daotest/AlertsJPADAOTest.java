package noesis.performancealerts.daotest;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.utils.Constants;

public class AlertsJPADAOTest {

	static Alerts alert;
	static AlertsJPADAO dao;
	public boolean persistido;

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass(){
	}

	@Before
	public void setUp() {
		alert = new Alerts();
		alert.setData(String.valueOf(new Timestamp(System.currentTimeMillis())));
		alert.setIdTest(TestJPADAO.getInstance().getAnyTest().getId());
		alert.setIdLastRun(RunJPADAO.getInstance().getAnyRun().getIdRun());
		alert.setSeverity(String.valueOf(Constants.GRAVIDADE_VIOLACAO_CRITICA));
		alert.setTypeError(String.valueOf(Constants.VIOLACAO_POR_INDISPONIBILIDADE));
		alert.setValue("0");
		dao = AlertsJPADAO.getInstance();
		persistido = false;
	}

	@After
	public void tearDown() {
		if (persistido) {
			dao.removeById(dao.findLastAlert().getId());
			persistido = false;
		}
	}

	@Test
	public void persist() {
		try {
			dao.persist(alert);
			persistido = true;
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
