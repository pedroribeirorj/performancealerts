package noesis.performancealerts.model;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import utils.Constants;
import utils.Mail;
import utils.Utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_alerts")
public class Alerts {
	@Id
	@GeneratedValue
	String id;
	@Column
	String id_test;
	@Column
	String id_run;
	@Column
	String data;
	@Column
	String typeError;
	@Column
	String severity;
	@Column
	String value;

	public Alerts() {
		// TODO Auto-generated constructor stub
	}

	public Alerts(String value, String id_run, String id_test, String data, String typeError, String severity) {
		this.id_test = id_test;
		this.id_run = id_run;
		this.data = data;
		this.typeError = typeError;
		this.severity = severity;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_test() {
		return id_test;
	}

	public void setId_test(String id_test) {
		this.id_test = id_test;
	}

	public String getId_run() {
		return id_run;
	}

	public void setId_run(String id_run) {
		this.id_run = id_run;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTypeError() {
		return typeError;
	}

	public void setTypeError(String typeError) {
		this.typeError = typeError;
	}

	public String getSeveity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
