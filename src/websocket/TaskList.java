package websocket;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PostPersist;
import javax.persistence.RollbackException;

import org.java_websocket.WebSocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class TaskList {
	
	WebSocket user;
	SocketData info,response;
	String INIT = "7000";	//init chat to new user
	String NEW_MSG = "7001";	//new messege sent notification
	String SEND_MSG = "7002";	//new messege sent
	   // JDBC driver name and database URL
	//static HashMap<WebSocket,String> onlineList = new HashMap<WebSocket,String>();
	static BiMap<String, WebSocket> onlineList = HashBiMap.create();

	
	TaskList(WebSocket user,SocketData data){
		this.user = user;
		this.info = data;		
		this.response = this.selectTask();
	}
	
	private SocketData selectTask(){
		if(info.header.equals(INIT)){			
			System.out.println("new user online : "+info.data);
			Date cur = new Date(Calendar.getInstance().getTime().getTime());
			
			 EntityManagerFactory emfactory;
			 EntityManager entitymanager;
				  emfactory = Persistence.createEntityManagerFactory( "websocket" );
				  entitymanager = emfactory.createEntityManager( );
			      entitymanager.getTransaction( ).begin( );
			      UserOnline u = new UserOnline();
			      u.setMemId(Integer.parseInt(info.data));
			      u.setTime(cur);	
			      entitymanager.persist(u);
			      try{
			    	  entitymanager.getTransaction( ).commit( ); 
			      }catch(RollbackException e){
			    	  System.out.println(e);
			      }
			      			    
			      onlineList.put(info.data,user);// <websocket,mem_id>
			      
			
			      return this.getResponse(info.header);
		     
			
			
			
			
			
		}
		else if(info.header.equals(this.NEW_MSG)){
			System.out.println("new msg received");
			if(onlineList.containsValue(info.getData())){
				System.out.println("guy is present");
				WebSocket temp = onlineList.get(info.getData());
				temp.send("you have new msg");
			}else{
				System.out.println("guy not present");				
			}
			
		}
		else if(info.header.equals(this.SEND_MSG)){
				        
				  Gson gson = new Gson(); 
				  SendMessage data = new SendMessage(); 
		          data = gson.fromJson(info.getData(), SendMessage.class); 
		          System.out.println("sending new msg"+data.getMessage()); 
		          int id = Integer.parseInt(onlineList.inverse().get(this.user)); 
		          Date cur = new Date(Calendar.getInstance().getTime().getTime()); 
		          EntityManagerFactory emfactory; 
		          EntityManager entitymanager; 
		          emfactory = Persistence.createEntityManagerFactory( "websocket" ); 
		          entitymanager = emfactory.createEntityManager( ); 
		          entitymanager.getTransaction( ).begin( ); 
		          MyMessage m = new MyMessage(); 
		          m.setMessage(data.getMessage()); 
		          m.setReceiver(data.getReceiver()); 
		          m.setSender(id); 
		          m.setDateTime(cur); 
		          entitymanager.persist( m ); 
		          entitymanager.getTransaction( ).commit( ); 
		          entitymanager.close();
		          String recev = Integer.toString(data.getReceiver()); 
		          System.out.println("rec : "+recev); 
		          SocketData reply = new SocketData();
		          reply.setData(new Gson().toJson(m));
		          reply.setHeader(this.genResponse(info.header));
		          String json = gson.toJson(reply); 
		          if(onlineList.containsKey(recev)){ 
			          System.out.println("guy is present"); 
			          WebSocket temp = onlineList.get(recev); 
			          temp.send(json); 
		          }else{ 
		          System.out.println("guy not present");         
		        }	 
	        
	        
	        
	        
		      
		      

			
		}
		return info;
	}
	
	
	private SocketData getResponse(String code){
		
		SocketData data = new SocketData();
		if(code.equals(INIT)){
			System.out.println("inside respond");
			data.setHeader(genResponse(INIT));
			data.setData("refresh chat");
			return data;
		}
		
		return data;
		
	}
	
	private String genResponse(String in){
		Integer temp = Integer.parseInt(in);
		temp =  temp+1000;
		return temp.toString();
	}
	
	private SocketData fromJSON(String message){
		Gson gson = new Gson();
        SocketData data = new SocketData();
        data = gson.fromJson(message, SocketData.class);
        return data;
	}
	
	private String toJSON(SocketData data){
		Gson gson = new Gson();
		String json = gson.toJson(data);
		return json;
	}
	
	private boolean sendMessage(String memId,String msg){
		
		if(onlineList.containsKey(memId)){
			WebSocket conn = onlineList.get(memId);
			conn.send(msg);
			
			
		}
		return true;
		
	}
	
	
	public SocketData deleteUser(WebSocket conn){
		
	     SocketData reply = new SocketData();
		System.out.println("this is get left"+onlineList.get(conn));
		int id = Integer.parseInt(onlineList.inverse().get(conn));
		EntityManagerFactory emfactory;
		EntityManager entitymanager;
		  emfactory = Persistence.createEntityManagerFactory( "websocket" );
		  entitymanager = emfactory.createEntityManager( );
		 entitymanager.getTransaction( ).begin( );      
		 UserOnline u = entitymanager.find( UserOnline.class, id );
	     entitymanager.remove( u );
	     entitymanager.getTransaction( ).commit();
	     entitymanager.close();
	     onlineList.inverse().remove(conn);
	     reply.setData("refresh chat");
         reply.setHeader(this.genResponse(INIT));
	     return reply;
		
	}
	
	
	

}
