package onlineQuiz.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USERID")
	private String userid;

	@Column(name="EMAIL")
	private String email;

	@Column(name="FIRSTNAME")
	private String firstname;

	@Column(name="LASTNAME")
	private String lastname;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="ROLE")
	private int role;

	public User() {
	}
	
	public User(String email, String fname, String lname, String pass, int role) {
		this.email = email;
		this.firstname = fname;
		this.lastname = lname;
		this.password = pass;
		this.role = role;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}