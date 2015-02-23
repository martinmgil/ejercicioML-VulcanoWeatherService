package ar.com.vulcano.weather.service.businessService;

import java.sql.SQLException;
import java.util.List;

import ar.com.vulcano.weather.service.dao.WeatherReportDao;
import ar.com.vulcano.weather.service.model.WeatherReport;

public class WeatherReportBusiness {
	public WeatherReport getClima(int dia) throws SQLException{
		WeatherReportDao dao = new WeatherReportDao();
		return dao.getWeatherReportByDay(dia);
	}
	
	public void saveWeatherCondition(List<WeatherReport> wrs) throws SQLException{
		WeatherReportDao dao = new WeatherReportDao();
		dao.saveWeatherCondition(wrs);
	}
}
