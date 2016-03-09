package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the Comments database table.
 * 
 */
@Entity
@Table(name="Comments")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COMMENTID")
	private String commentid;

	private BigInteger recordId;

	@Column(name="TEXT")
	private String text;

	public Comment() {
	}

	public String getCommentid() {
		return this.commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public BigInteger getRecordId() {
		return this.recordId;
	}

	public void setRecordId(BigInteger recordId) {
		this.recordId = recordId;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}