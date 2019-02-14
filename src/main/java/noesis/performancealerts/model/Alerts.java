package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import noesis.performancealerts.utils.Utils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "tbl_alerts")
public class Alerts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_test")
	@Column(name = "id", unique = true, nullable = false)
	@SequenceGenerator(name = "id_test", sequenceName = "seq_tbl_alerts", allocationSize = 1)
	int id;
	@Column(name = "id_test")
	int idTest;
	@Column(name = "id_last_run")
	int idLastRun;
	@Column
	String data;
	@Column
	String typeError;
	@Column
	String severity;
	@Column
	String value;

	public Alerts() {
		// Classe de modelo da tabela alertas (tbl_alerts).
	}

	public int getIdLastRun() {
		return idLastRun;
	}

	public void setIdLastRun(int idLastRun) {
		this.idLastRun = idLastRun;
	}

	public String getSeverity() {
		return severity;
	}

	public Alerts(String value, int idTest, String data, String typeError, String severity, int lastRun) {
		this.idTest = idTest;
		this.data = data;
		this.typeError = typeError;
		this.severity = severity;
		this.value = value;
		this.idLastRun = lastRun;
	}

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
