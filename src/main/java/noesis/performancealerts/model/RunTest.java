package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_runs_tests")
public class RunTest {
	@Id
	int id;
	@Column
	int id_test;
	@Column
	int id_run;
	@Column
	int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_test() {
		return id_test;
	}
	public void setId_test(int id_test) {
		this.id_test = id_test;
	}
	public int getId_run() {
		return id_run;
	}
	public void setId_run(int id_run) {
		this.id_run = id_run;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
