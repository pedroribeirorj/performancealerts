package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_test")
public class Test {
	@Id
	int id	;
	@Column
	int fk_app_type_id	;
	@Column
	int fk_repository_type_id	;
	@Column
	String fk_repository_servers_id	;
	@Column
	Timestamp created_date	;
	@Column
	Timestamp updated_date	;
	@Column
	String name_test	 ;
	@Column
	String description		;
	@Column
	String test_version_id	;
	@Column
	String test_case_id		;
	@Column
	String test_cycle_id	;
	@Column
	String username			;
	@Column
	String project_id		;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFk_app_type_id() {
		return fk_app_type_id;
	}
	public void setFk_app_type_id(int fk_app_type_id) {
		this.fk_app_type_id = fk_app_type_id;
	}
	public int getFk_repository_type_id() {
		return fk_repository_type_id;
	}
	public void setFk_repository_type_id(int fk_repository_type_id) {
		this.fk_repository_type_id = fk_repository_type_id;
	}
	public String getFk_repository_servers_id() {
		return fk_repository_servers_id;
	}
	public void setFk_repository_servers_id(String fk_repository_servers_id) {
		this.fk_repository_servers_id = fk_repository_servers_id;
	}
	public Timestamp getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	public Timestamp getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Timestamp updated_date) {
		this.updated_date = updated_date;
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
		return test_version_id;
	}
	public void setTest_version_id(String test_version_id) {
		this.test_version_id = test_version_id;
	}
	public String getTest_case_id() {
		return test_case_id;
	}
	public void setTest_case_id(String test_case_id) {
		this.test_case_id = test_case_id;
	}
	public String getTest_cycle_id() {
		return test_cycle_id;
	}
	public void setTest_cycle_id(String test_cycle_id) {
		this.test_cycle_id = test_cycle_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	
	
}
