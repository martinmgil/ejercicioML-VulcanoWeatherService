package ar.com.vulcano.weather.service.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionUtil {
	public Connection getConection() throws SQLException;
	public void closeConection() throws SQLException;
}
