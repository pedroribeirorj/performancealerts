package noesis.performancealerts.model;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_runs")
public class Run {
	@Id
	@Column(name="id_run")
	int idRun;
	@Column
	Timestamp data;
	@Column(name="cycle_id")
	String cycleId;
	@Column(name="version_id")
	String versionId;
	@Column(name="projectId")
	String projectId;
	@Column(name="fk_appType")
	int fkAappType;
	@Column(name="fk_repository_servers_id")
	int fkRepositoryServersId;
	
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public int getIdRun() {
		return idRun;
	}
	public void setIdRun(int idRun) {
		this.idRun = idRun;
	}
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public int getFkAappType() {
		return fkAappType;
	}
	public void setFkAappType(int fkAappType) {
		this.fkAappType = fkAappType;
	}
	public int getFkRepositoryServersId() {
		return fkRepositoryServersId;
	}
	public void setFkRepositoryServersId(int fkRepositoryServersId) {
		this.fkRepositoryServersId = fkRepositoryServersId;
	}
	
	
}
