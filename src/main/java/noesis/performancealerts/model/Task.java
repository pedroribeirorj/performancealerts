package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tasks")
public class Task {
	@Column
	boolean enabled;
	@Column
	boolean removed;
	@Column
	Timestamp updated_date;
	@Column
	Timestamp created_date;
	@Column
	Timestamp startDate;
	@Column
	Timestamp endDate;
	@Id
	int task_id;
	@Column
	int machine_id;
	
	@Column(nullable=false, columnDefinition="int")
	String device_id;
	
	@Column
	int fk_app_type_id;
	@Column
	int fk_repository_type_id;
	@Column
	int fk_repository_servers_id;
	@Column(nullable=false, columnDefinition="int")
	String recurrence;
	@Column
	String task_name;
	@Column
	String test_version_id;
	@Column
	String test_cycle_id;
	@Column
	String test_plan_key;
	@Column
	String username;
	@Column
	String frequency;

	@Column
	String project_id;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Timestamp getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Timestamp updated_date) {
		this.updated_date = updated_date;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getMachine_id() {
		return machine_id;
	}

	public void setMachine_id(int machine_id) {
		this.machine_id = machine_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
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

	public int getFk_repository_servers_id() {
		return fk_repository_servers_id;
	}

	public void setFk_repository_servers_id(int fk_repository_servers_id) {
		this.fk_repository_servers_id = fk_repository_servers_id;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTest_version_id() {
		return test_version_id;
	}

	public void setTest_version_id(String test_version_id) {
		this.test_version_id = test_version_id;
	}

	public String getTest_cycle_id() {
		return test_cycle_id;
	}

	public void setTest_cycle_id(String test_cycle_id) {
		this.test_cycle_id = test_cycle_id;
	}

	public String getTest_plan_key() {
		return test_plan_key;
	}

	public void setTest_plan_key(String test_plan_key) {
		this.test_plan_key = test_plan_key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
}
