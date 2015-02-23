package ar.com.vulcano.weather.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class VulcanoGalaxyWeatherServiceServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("--Servlet no disponible--");
		resp.getWriter().println("Para ejecutar el servicio seguir la siguiente url:");
		resp.getWriter().println("http://1-dot-martingilmltest.appspot.com/rest/clima/dia/[dia a seleccionar]");
		resp.getWriter().println("donde [dia a seleccionar es un entero que determina el día a partir de 1 en adelante hasta 10 años (3650 días)");
		resp.getWriter().println("");
		resp.getWriter().println("Ej: http://1-dot-martingilmltest.appspot.com/rest/clima/dia/214");
		//Conecta con la base
//		try {
//			String conectionString = "";
//			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {				  
//				  conectionString = "jdbc:google:rdbms://martingilmltest:mysqlmldb/ml_weather_report_db";
//				} else {
//				  conectionString = "jdbc:mysql://127.0.0.1:3306/ml_weather_report_db?user=root";
//				}
//			Connection conn = DriverManager.getConnection(conectionString);
//			Statement statement = conn.createStatement();
// 
//			// execute select SQL stetement
//			ResultSet rs = statement.executeQuery("SELECT ID, DIA, CLIMA FROM predicciones_clima");
// 
//			while (rs.next()) {
// 
//				Long id = rs.getLong("ID");
//				Integer dia = rs.getInt("DIA");
//				String clima = rs.getString("CLIMA");
//				resp.getWriter().println(
//				          id + " " +
//				          dia + " " +
//				          clima); 
//			}
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
