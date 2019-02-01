package noesis.performancealerts.model;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_runs")
public class Run {
	@Id
	int id_run;
	@Column
	Timestamp data;
	@Column
	String cycle_id;
	@Column
	String version_id;
	@Column
	String project_id;
	@Column
	int fk_appType;
	@Column
	int fk_repository_servers_id;
	
	public int getId_run() {
		return id_run;
	}
	public void setId_run(int id_run) {
		this.id_run = id_run;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getCycle_id() {
		return cycle_id;
	}
	public void setCycle_id(String cycle_id) {
		this.cycle_id = cycle_id;
	}
	public String getVersion_id() {
		return version_id;
	}
	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public int getFk_appType() {
		return fk_appType;
	}
	public void setFk_appType(int fk_appType) {
		this.fk_appType = fk_appType;
	}
	public int getFk_repository_servers_id() {
		return fk_repository_servers_id;
	}
	public void setFk_repository_servers_id(int fk_repository_servers_id) {
		this.fk_repository_servers_id = fk_repository_servers_id;
	}
	
	
}
