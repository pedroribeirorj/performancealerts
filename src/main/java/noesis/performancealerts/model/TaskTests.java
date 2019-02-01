package noesis.performancealerts.model;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tasks_tests")
public class TaskTests {
	@Column
	int removed;
	@Id
	boolean id;
	@Column
	int fk_test_id;
	
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	public boolean isId() {
		return id;
	}
	public void setId(boolean id) {
		this.id = id;
	}
	public int getFk_test_id() {
		return fk_test_id;
	}
	public void setFk_test_id(int fk_test_id) {
		this.fk_test_id = fk_test_id;
	}
	
	
}
