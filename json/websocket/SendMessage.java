package websocket;

public class SendMessage {

	String message;
	int receiver;
	
	void setMessage(String message){
		this.message = message;
	}
	
	void setReceiver(String receiver){
		this.receiver = Integer.parseInt(receiver);
	}
	
	String getMessage(){
		return this.message;
	}
	
	int getReceiver(){
		return this.receiver;
	}
}
