package ar.com.vulcano.weather.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class JDBCConectionUtil {
	public static Connection getConection() throws SQLException{
		String conectionString = "";
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {				  
			  conectionString = "jdbc:google:rdbms://martingilmltest:mysqlmldb/ml_weather_report_db";
		} else {
			  conectionString = "jdbc:mysql://127.0.0.1:3306/ml_weather_report_db?user=root";
		}
		Connection conn = DriverManager.getConnection(conectionString);
		return conn;
	}
}
