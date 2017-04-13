package websocket;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the friendship database table.
 * 
 */
@Embeddable
public class FriendshipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int sender;

	private int receiver;

	public FriendshipPK() {
	}
	public int getSender() {
		return this.sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getReceiver() {
		return this.receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FriendshipPK)) {
			return false;
		}
		FriendshipPK castOther = (FriendshipPK)other;
		return 
			(this.sender == castOther.sender)
			&& (this.receiver == castOther.receiver);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sender;
		hash = hash * prime + this.receiver;
		
		return hash;
	}
}