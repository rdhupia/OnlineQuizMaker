package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the AnswerChoices database table.
 * 
 */
@Entity
@Table(name="AnswerChoices")
@NamedQuery(name="AnswerChoice.findAll", query="SELECT a FROM AnswerChoice a")
public class AnswerChoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ANSID")
	private long ansid;

	@Column(name="ANSWER")
	private String answer;

	@Column(name="CORRECT")
	private int correct;

	@Column(name="NUMOFTIMESSELECTED")
	private int numoftimesselected;

	private BigInteger quesId;

	public AnswerChoice() {
	}

	public long getAnsid() {
		return this.ansid;
	}

	public void setAnsid(long ansid) {
		this.ansid = ansid;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCorrect() {
		return this.correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getNumoftimesselected() {
		return this.numoftimesselected;
	}

	public void setNumoftimesselected(int numoftimesselected) {
		this.numoftimesselected = numoftimesselected;
	}

	public BigInteger getQuesId() {
		return this.quesId;
	}

	public void setQuesId(BigInteger quesId) {
		this.quesId = quesId;
	}

}