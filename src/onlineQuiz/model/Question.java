package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the Questions database table.
 * 
 */
@Entity
@Table(name="Questions")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="QUESID")
	private long quesid;

	@Column(name="ANSWEREXPLAINED")
	private String answerexplained;

	@Column(name="DIFFICULTYLEVEL")
	private int difficultylevel;

	@Column(name="HINT")
	private String hint;

	@Column(name="NUMOFTIMESASKED")
	private int numoftimesasked;

	@Column(name="QUESTION")
	private String question;

	private BigInteger questionTypeId;

	private BigInteger quizId;

	public Question() {
	}

	public Question(long quesid, String answerexplained, int difficultylevel, String hint, int numoftimesasked,
			String question, BigInteger questionTypeId, BigInteger quizId) {
		this.quesid = quesid;
		this.answerexplained = answerexplained;
		this.difficultylevel = difficultylevel;
		this.hint = hint;
		this.numoftimesasked = numoftimesasked;
		this.question = question;
		this.questionTypeId = questionTypeId;
		this.quizId = quizId;
	}

	public long getQuesid() {
		return this.quesid;
	}

	public void setQuesid(long quesid) {
		this.quesid = quesid;
	}

	public String getAnswerexplained() {
		return this.answerexplained;
	}

	public void setAnswerexplained(String answerexplained) {
		this.answerexplained = answerexplained;
	}

	public int getDifficultylevel() {
		return this.difficultylevel;
	}

	public void setDifficultylevel(int difficultylevel) {
		this.difficultylevel = difficultylevel;
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public int getNumoftimesasked() {
		return this.numoftimesasked;
	}

	public void setNumoftimesasked(int numoftimesasked) {
		this.numoftimesasked = numoftimesasked;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public BigInteger getQuestionTypeId() {
		return this.questionTypeId;
	}
	
	public int getQuestionType() {
		return this.questionTypeId.intValue();
	}

	public void setQuestionTypeId(BigInteger questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public BigInteger getQuizId() {
		return this.quizId;
	}

	public void setQuizId(BigInteger quizId) {
		this.quizId = quizId;
	}

}