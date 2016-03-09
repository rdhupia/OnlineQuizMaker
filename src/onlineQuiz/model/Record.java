package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the Records database table.
 * 
 */
@Entity
@Table(name="Records")
@NamedQuery(name="Record.findAll", query="SELECT r FROM Record r")
public class Record implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RECORDID")
	private String recordid;

	@Temporal(TemporalType.DATE)
	@Column(name="DATEOFQUIZ")
	private Date dateofquiz;

	private BigInteger quizId;

	@Column(name="SCORE")
	private int score;

	private BigInteger userId;

	public Record() {
	}

	public String getRecordid() {
		return this.recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public Date getDateofquiz() {
		return this.dateofquiz;
	}

	public void setDateofquiz(Date dateofquiz) {
		this.dateofquiz = dateofquiz;
	}

	public BigInteger getQuizId() {
		return this.quizId;
	}

	public void setQuizId(BigInteger quizId) {
		this.quizId = quizId;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

}