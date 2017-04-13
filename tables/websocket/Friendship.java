package websocket;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the friendship database table.
 * 
 */
@Entity
@Table(name="friendship")
@NamedQuery(name="Friendship.findAll", query="SELECT f FROM Friendship f")
public class Friendship implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FriendshipPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_time")
	private Date dateTime;

	private int status;

	public Friendship() {
	}

	public FriendshipPK getId() {
		return this.id;
	}

	public void setId(FriendshipPK id) {
		this.id = id;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}