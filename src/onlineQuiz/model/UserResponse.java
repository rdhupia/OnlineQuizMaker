package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the UserResponses database table.
 * 
 */
@Entity
@Table(name="UserResponses")
@NamedQuery(name="UserResponse.findAll", query="SELECT u FROM UserResponse u")
public class UserResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESPONSEID")
	private String responseid;

	private BigInteger ansId;

	private BigInteger quesId;

	private BigInteger recordId;

	public UserResponse() {
	}

	public String getResponseid() {
		return this.responseid;
	}

	public void setResponseid(String responseid) {
		this.responseid = responseid;
	}

	public BigInteger getAnsId() {
		return this.ansId;
	}

	public void setAnsId(BigInteger ansId) {
		this.ansId = ansId;
	}

	public BigInteger getQuesId() {
		return this.quesId;
	}

	public void setQuesId(BigInteger quesId) {
		this.quesId = quesId;
	}

	public BigInteger getRecordId() {
		return this.recordId;
	}

	public void setRecordId(BigInteger recordId) {
		this.recordId = recordId;
	}

}