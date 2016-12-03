package websocket;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PostPersist;

import org.java_websocket.WebSocket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class TaskList {
	
	WebSocket user;
	SocketData info,response;
	EntityManagerFactory emfactory;
	EntityManager entitymanager;
	String INIT = "7000";	//init chat to new user
	String NEW_MSG = "7001";	//new messege sent
	   // JDBC driver name and database URL
	//static HashMap<WebSocket,String> onlineList = new HashMap<WebSocket,String>();
	static BiMap<WebSocket, String> onlineList = HashBiMap.create();
	
	TaskList(WebSocket user,SocketData data){
		this.user = user;
		this.info = data;		
		emfactory = Persistence.createEntityManagerFactory( "websocket" );
		entitymanager = emfactory.createEntityManager( );
		this.response = this.selectTask();
	}
	
	private SocketData selectTask(){
		if(info.header.equals(INIT)){
			System.out.println("new user online : "+info.data);
			Date cur = new Date(Calendar.getInstance().getTime().getTime());
			
			

		      entitymanager.getTransaction( ).begin( );
		      UserOnline u = new UserOnline();
		      u.setMemId(Integer.parseInt(info.data));
		      u.setTime(cur);	      
		      entitymanager.persist( u );
		      entitymanager.getTransaction( ).commit( );    
		      onlineList.put(user, info.data);
		      return this.getResponse(info.header);
		     
			
			
			
			
			
		}
		else if(info.header.equals(this.NEW_MSG)){
			System.out.println("new msg received");
			if(onlineList.containsValue(info.getData())){
				System.out.println("guy is present");
				WebSocket temp = onlineList.inverse().get(info.getData());
				temp.send("you have new msg");
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
			data.setHeader("8000");
			data.setData("refresh chat");
			return data;
		}
		
		return data;
		
	}
	
	public void deleteUser(WebSocket conn){
		
		System.out.println("this is get left"+onlineList.get(conn));
		int id = Integer.parseInt(onlineList.get(conn));
		 entitymanager.getTransaction( ).begin( );      
		 UserOnline u = entitymanager.find( UserOnline.class, id );
	     entitymanager.remove( u );
	     entitymanager.getTransaction( ).commit();
	     onlineList.remove(conn);
		
	}
	
	

}
