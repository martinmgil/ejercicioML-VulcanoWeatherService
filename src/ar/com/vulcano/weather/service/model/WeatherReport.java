package ar.com.vulcano.weather.service.model;


//@Entity
//@Table(name = "predicciones_clima")
public class WeatherReport {
	private long id;
	private int dia;	
	private String clima;
	
	public WeatherReport(int dia, String clima){
		this.dia = dia;
		this.clima = clima;
	}
	
	public WeatherReport(){}
	
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}	
	
	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("  ** Id: ").append(getId());
        sb.append("- Dia: ").append(getDia());
        sb.append("- Clima: ").append(getClima());
        return sb.toString();
	}	
}
