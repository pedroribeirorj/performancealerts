package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_task_log")
public class TaskLog {
	@Column
	Timestamp timeEntered	;
	@Id
	int id	;
	@Column
	int taskID	;
	@Column
	String taskName;
	@Column
	String urlZephyr;
	@Column
	String message;
	public Timestamp getTimeEntered() {
		return timeEntered;
	}
	public void setTimeEntered(Timestamp timeEntered) {
		this.timeEntered = timeEntered;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getUrlZephyr() {
		return urlZephyr;
	}
	public void setUrlZephyr(String urlZephyr) {
		this.urlZephyr = urlZephyr;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
