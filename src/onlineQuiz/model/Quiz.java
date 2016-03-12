package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the Quizzes database table.
 * 
 */
@Entity
@Table(name="Quizzes")
@NamedQuery(name="Quiz.findAll", query="SELECT q FROM Quiz q")
public class Quiz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="QUIZID")
	private long quizid;

	@Column(name="DIFFICULTQS")
	private int difficultqs;

	@Column(name="EASYQS")
	private int easyqs;

	@Column(name="MEDIUMQS")
	private int mediumqs;

	@Column(name="QUIZDESCRIPTION")
	private String quizdescription;

	@Column(name="QUIZNAME")
	private String quizname;

	private BigInteger subjectId;

	public Quiz() {
	}

	public long getQuizid() {
		return this.quizid;
	}

	public void setQuizid(long quizid) {
		this.quizid = quizid;
	}

	public int getDifficultqs() {
		return this.difficultqs;
	}

	public void setDifficultqs(int difficultqs) {
		this.difficultqs = difficultqs;
	}

	public int getEasyqs() {
		return this.easyqs;
	}

	public void setEasyqs(int easyqs) {
		this.easyqs = easyqs;
	}

	public int getMediumqs() {
		return this.mediumqs;
	}

	public void setMediumqs(int mediumqs) {
		this.mediumqs = mediumqs;
	}

	public String getQuizdescription() {
		return this.quizdescription;
	}

	public void setQuizdescription(String quizdescription) {
		this.quizdescription = quizdescription;
	}

	public String getQuizname() {
		return this.quizname;
	}

	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}

	public BigInteger getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(BigInteger subjectId) {
		this.subjectId = subjectId;
	}

}