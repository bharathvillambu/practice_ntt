package com.application.TrainingInstituteAppFinal;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDataBase {
	public static Connection getConnection() {
		FileInputStream fis = null;

		Connection c = null;
		try {
			fis = new FileInputStream("C:\\Users\\HP\\eclipse-workspace\\TrainingInstituteAppFinal\\src\\main\\java\\com\\application\\TrainingInstituteAppFinal\\configuration.properties");
			Properties p = new Properties();
			p.load(fis);
			Class.forName(p.getProperty("driverclass"));
			c = null;
			c = DriverManager.getConnection(p.getProperty("url"), p.getProperty("user"), p.getProperty("pass"));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
