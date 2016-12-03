package websocket;

public class SocketData {
	String header;
	String data;
	
	void setHeader(String header){
		this.header = header;
	}
	
	void setData(String data){
		this.data = data;
	}
	
	String getHeader(){
		return this.header;
	}
	
	String getData(){
		return this.data;
	}

}
