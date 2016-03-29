package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the QuestionTypes database table.
 * 
 */
@Entity
@Table(name="QuestionTypes")
@NamedQuery(name="QuestionType.findAll", query="SELECT q FROM QuestionType q")
public class QuestionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TYPEID")
	private long typeid;									// 1 MCQ, 2 T/F, 3 Input text, 4 Multi-select

	@Column(name="TYPEDESCRIP")
	private String typedescrip;

	@Column(name="TYPENAME")
	private String typename;

	public QuestionType() {
	}

	public QuestionType(long typeid, String typedescrip, String typename) {
		this.typeid = typeid;
		this.typedescrip = typedescrip;
		this.typename = typename;
	}

	public long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public String getTypedescrip() {
		return this.typedescrip;
	}

	public void setTypedescrip(String typedescrip) {
		this.typedescrip = typedescrip;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}