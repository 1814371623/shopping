package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConn {
	
	public static Connection getConn() {
    	Connection connection = null;
    	String driverClass = null;
    	String jdbcUrl = null;
    	String user = null;
    	String password = null;
    	
    	//��ȡ�ļ�,������ݿ�������jdbc.properties�ļ����޸�
    	Properties properties = new Properties();
    	InputStream in =  
    			DbConn.class.getClassLoader().getResourceAsStream("db/jdbc.properties");
    	//��Ҫд�� getclass.getClassLoader().getResourceAsStream
    	try{
    		properties.load(in);
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	driverClass = properties.getProperty("driverClass");
    	jdbcUrl = properties.getProperty("jdbcUrl");
    	user = properties.getProperty("user");
    	password = properties.getProperty("password");
    	
    	//����������������
    	try{
    		Class.forName(driverClass);
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	try{
    		connection = DriverManager.getConnection(jdbcUrl, user, password);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return connection;
    }
}
