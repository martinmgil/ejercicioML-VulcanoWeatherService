package ar.com.vulcano.weather.service.converter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



import javax.xml.bind.annotation.XmlType;

import ar.com.vulcano.weather.service.model.WeatherReport;

@XmlRootElement(name = "weatherReport")
@XmlType(propOrder = {"dia", "clima"})
public class WeatherReportConverter {
	private WeatherReport entity = null;
    public WeatherReportConverter() {
    	entity = new WeatherReport();
	}

    public WeatherReportConverter(WeatherReport entity) {
    	this.entity = entity;
	}
    
    public WeatherReport getWeatherReport(){
    	return entity;
    }
    
    @XmlElement
    public String getClima() {
    	return entity.getClima();
    }
    
    @XmlElement
    public int getDia() {
    	return entity.getDia();
    }
       
    
    public void setDia(int dia){
    	entity.setDia(dia);
    }
    
    public void setClima(String clima){
    	entity.setClima(clima);
    }
}
