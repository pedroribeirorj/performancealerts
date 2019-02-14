package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(name = "tbl_runs_tests")
public class RunTest {
	@Id
	int id;
	@Column(name = "id_test")
	int idTest;
	@Column(name = "id_run")
	int idRun;
	@Column
	int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTest() {
		return idTest;
	}

	public void setIdTest(int idTest) {
		this.idTest = idTest;
	}

	public int getId_run() {
		return idRun;
	}

	public void setIdRun(int idRun) {
		this.idRun = idRun;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPassed() {
		return status == noesis.performancealerts.utils.Constants.STATUS_PASSED;
	}

}
