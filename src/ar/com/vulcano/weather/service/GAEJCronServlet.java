package ar.com.vulcano.weather.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.vulcano.weather.service.job.WeatherReportJob;

@SuppressWarnings("serial")
public class GAEJCronServlet  extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		WeatherReportJob job = new WeatherReportJob();
		
		//Cantidad de días que tienen 10 años
		//TODO: sacar afuera de las clases, a un archivo de configuración (como mejora)
		job.populateDB(3650);
	}
}
	