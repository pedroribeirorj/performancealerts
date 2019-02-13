package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(name = "tbl_test")
public class Test {
	@Id
	int id;
	@Column(name = "fk_app_type_id")
	int fkAppTypeId;
	@Column(name = "fk_repository_type_id")
	int fkRepositoryTypeId;
	@Column(name = "fk_repository_servers_id")
	String fkRepositoryServersId;
	@Column(name = "created_date")
	Timestamp createdDate;
	@Column(name = "updated_date")
	Timestamp updatedDate;
	@Column(name = "name_test")
	String name_test;
	@Column(name = "description")
	String description;
	@Column(name = "test_version_id")
	String testVersionId;
	@Column(name = "test_case_id")
	String testCaseId;
	@Column(name = "test_cycle_id")
	String testCycleId;
	@Column(name = "username")
	String username;
	@Column(name = "project_id")
	String projectId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFk_app_type_id() {
		return fkAppTypeId;
	}

	public void setFk_app_type_id(int fk_app_type_id) {
		this.fkAppTypeId = fk_app_type_id;
	}

	public int getFk_repository_type_id() {
		return fkRepositoryTypeId;
	}

	public void setFk_repository_type_id(int fk_repository_type_id) {
		this.fkRepositoryTypeId = fk_repository_type_id;
	}

	public String getFk_repository_servers_id() {
		return fkRepositoryServersId;
	}

	public void setFk_repository_servers_id(String fk_repository_servers_id) {
		this.fkRepositoryServersId = fk_repository_servers_id;
	}

	public Timestamp getCreated_date() {
		return createdDate;
	}

	public void setCreated_date(Timestamp created_date) {
		this.createdDate = created_date;
	}

	public Timestamp getUpdated_date() {
		return updatedDate;
	}

	public void setUpdated_date(Timestamp updated_date) {
		this.updatedDate = updated_date;
	}

	public String getName_test() {
		return name_test;
	}

	public void setName_test(String name_test) {
		this.name_test = name_test;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTest_version_id() {
		return testVersionId;
	}

	public void setTest_version_id(String test_version_id) {
		this.testVersionId = test_version_id;
	}

	public String getTest_case_id() {
		return testCaseId;
	}

	public void setTest_case_id(String test_case_id) {
		this.testCaseId = test_case_id;
	}

	public String getTest_cycle_id() {
		return testCycleId;
	}

	public void setTest_cycle_id(String test_cycle_id) {
		this.testCycleId = test_cycle_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProject_id() {
		return projectId;
	}

	public void setProject_id(String project_id) {
		this.projectId = project_id;
	}

}
