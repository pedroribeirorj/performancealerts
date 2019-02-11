package noesis.performancealerts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
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
	@Column(name="id_test")
	int idTest;
	@Column(name="id_run")
	int idRun;
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

	public Alerts(String value, int idRun, int idTest, String data, String typeError, String severity) {
		this.idTest = idTest;
		this.idRun = idRun;
		this.data = data;
		this.typeError = typeError;
		this.severity = severity;
		this.value = value;
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

	public int getIdRun() {
		return idRun;
	}

	public void setIdRun(int idRun) {
		this.idRun = idRun;
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
