package ar.com.vulcano.weather.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ar.com.vulcano.weather.service.model.WeatherReport;

public class WeatherReportDao {
	/**
	 * Obtiene el reporte del día "dia"
	 * @param dia
	 * @return
	 * @throws SQLException
	 */
	public WeatherReport getWeatherReportByDay(int dia) throws SQLException{
		Connection conn = JDBCConectionUtil.getConection();

		PreparedStatement statement = conn.prepareStatement("SELECT ID, DIA, CLIMA FROM predicciones_clima WHERE DIA = ?" );
		statement.setInt(1, dia);
		
		ResultSet rs = statement.executeQuery();

		WeatherReport wr = new WeatherReport();
		while (rs.next()) {
			wr.setId(rs.getLong("ID"));
			wr.setDia(rs.getInt("DIA"));
			wr.setClima(rs.getString("CLIMA"));
		}
		conn.close();
		return wr;
	}
	
	/**
	 * Guarda en la DB el reporte del día
	 * @param wr
	 * @throws SQLException
	 */
	public void saveWeatherCondition(List<WeatherReport> wrs) throws SQLException{
		Connection conn = null;
		PreparedStatement stmtDelete = null;
		PreparedStatement stmtSelect = null;
		try{
			conn = JDBCConectionUtil.getConection();
			conn.setAutoCommit(false);
			//Borra la tabla
			stmtDelete = conn.prepareStatement("DELETE FROM predicciones_clima");			
			stmtDelete.executeUpdate();
			
			//Inserta los registros
			//TODO: usar otro tipo de autonumérico. En el ejemplo, sirve.
			Long id = 0L;
			for (WeatherReport wr : wrs) {
				id++;
				stmtSelect = conn.prepareStatement("INSERT INTO predicciones_clima VALUES(?,?,?)");
				stmtSelect.setLong(1, id);
				stmtSelect.setInt(2, wr.getDia());
				stmtSelect.setString(3, wr.getClima());
				stmtSelect.executeUpdate();				
		    }
			conn.commit();
		}catch(SQLException ex){
			if (conn != null) {
                try {
                    System.err.print("-->Rollback");
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
        	try{
        		conn.close();
        	}catch(Exception e){
        		System.err.print("-->No pudo cerrarse la conexión");
        	}
            if (stmtDelete != null) {
            	stmtDelete.close();
            }
            if (stmtSelect!= null) {
            	stmtSelect.close();
            }
            conn.setAutoCommit(true);
        }
	}

	/**
	 * 
	 * @param dia
	 * @return
	 * @throws SQLException
	 */
	public WeatherReport getLastReport() throws SQLException{
		Connection conn = JDBCConectionUtil.getConection();

		Statement statement = conn.createStatement();
		String sql = "SELECT MAX(DIA) FROM predicciones_clima";
		ResultSet rs = statement.executeQuery(sql);

		WeatherReport wr = new WeatherReport();
		while (rs.next()) {
			wr.setId(0);
			wr.setDia(rs.getInt("DIA"));
			wr.setClima("");
		}
		conn.close();
		return wr;
	}
	
	

}
