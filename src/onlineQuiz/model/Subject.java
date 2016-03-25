package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Subject database table.
 * 
 */
@Entity
@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s")
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SUBJECTID")
	private long subjectid;

	@Column(name="SUBJECTDESCRIPTION")
	private String subjectdescription;

	@Column(name="SUBJECTNAME")
	private String subjectname;

	public Subject() {
	}

	public long getSubjectid() {
		return this.subjectid;
	}

	public Subject(long subjectid, String subjectdescription, String subjectname) {
		this.subjectid = subjectid;
		this.subjectdescription = subjectdescription;
		this.subjectname = subjectname;
	}

	public void setSubjectid(long subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubjectdescription() {
		return this.subjectdescription;
	}

	public void setSubjectdescription(String subjectdescription) {
		this.subjectdescription = subjectdescription;
	}

	public String getSubjectname() {
		return this.subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

}