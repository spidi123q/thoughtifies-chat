package websocket;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_online database table.
 * 
 */
@Entity
@Table(name="user_online")
@NamedQuery(name="UserOnline.findAll", query="SELECT u FROM UserOnline u")
public class UserOnline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mem_id")
	private int memId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public UserOnline() {
	}

	public int getMemId() {
		return this.memId;
	}

	public void setMemId(int memId) {
		this.memId = memId;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@PostPersist
    public void postPersist(){
        System.out.println("db action completed");
    }

}