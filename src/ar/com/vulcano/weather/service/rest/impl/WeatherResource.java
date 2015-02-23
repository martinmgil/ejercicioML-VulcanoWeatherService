package ar.com.vulcano.weather.service.rest.impl;
import java.sql.SQLException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ar.com.vulcano.weather.service.businessService.WeatherReportBusiness;
import ar.com.vulcano.weather.service.converter.WeatherReportConverter;
import ar.com.vulcano.weather.service.model.WeatherReport;

@Path("/clima/")
public class WeatherResource {
	@GET
	@Produces({"application/json"})
	@Path("/dia/{dia}/")
	public WeatherReportConverter getClima(@PathParam("dia") int dia) {
		WeatherReportBusiness wrb = new WeatherReportBusiness();		
		WeatherReport weatherReport;
		WeatherReportConverter converter = null;
		try {
			weatherReport = wrb.getClima(dia);
			if(weatherReport.getClima() == null){
				weatherReport.setId(0);
				weatherReport.setDia(dia);
				weatherReport.setClima("--No Informado--");
			}
			
			converter = new WeatherReportConverter(weatherReport);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print("-->Error en la DB: no pudo obtenerse el clima para el dia " + dia + " *** Detalle: " + e.getMessage());
		}catch(Exception e){
			System.err.print("-->No pudo obtenerse el clima para ese día. Vuelva a intentarlo. *** Detalle: " + e.getMessage());
		}
		
		return converter;
	}
}
