package websocket;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the myMessages database table.
 * 
 */
@Entity
@Table(name="myMessages")
@NamedQuery(name="MyMessage.findAll", query="SELECT m FROM MyMessage m")
public class MyMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_time")
	private Date dateTime;

	private String message;

	private int receiver;

	private int seen;

	private int sender;

	public MyMessage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getReceiver() {
		return this.receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getSeen() {
		return this.seen;
	}

	public void setSeen(int seen) {
		this.seen = seen;
	}

	public int getSender() {
		return this.sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

}