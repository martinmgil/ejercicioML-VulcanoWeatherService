package ar.com.vulcano.weather.service.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.vulcano.weather.service.businessService.WeatherReportBusiness;
import ar.com.vulcano.weather.service.model.WeatherReport;

public class WeatherReportJob {
	public static int velicidadFerengi = 1;
	public static int velocidadBetasoide = 3;
	public static int velocidadVulcano = 5;
	
	public static int distanciaFerengi = 500;
	public static int distanciaBetasoide = 1500;
	public static int distanciaVulcano = 1000;
	
	public static int gradosCiclo = 360;
	
	/**
	 * Carga inicial del período según la cantidad de días pasados por parámetro
	 * Elimina la carga actual de la tabla
	 * @param daysQuantity: cantidad de días que van a crearse
	 */
	public void populateDB(int daysQuantity){
		//Borra la tabla y realiza los n-inserts
		WeatherReportBusiness wrb = new WeatherReportBusiness();
		try{
			wrb.saveWeatherCondition(getCalculations(3650));
		}catch(SQLException ex){
			System.err.print("-->Error al borrar, persistir o intentar conectarse a la DB");
		}catch(Exception ex){
			System.err.print("-->No pudo realizarse el cálculo de las condiciones climáticas");
		}	
	}
	
	public static void main(String[] args) {
		WeatherReportJob wrj = new WeatherReportJob();
		//Persistencia DB
		WeatherReportBusiness wrb = new WeatherReportBusiness();
		try {
			wrb.saveWeatherCondition(wrj.getCalculations(3650));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Cálculos
//		List<WeatherReport>wrs = wrj.getCalculations(3650);
//		for(WeatherReport wr : wrs){
//			System.out.println(wr.toString());
//		}
		
	}
	
	
	
	/**
	 * Realiza el cálculo de condiciones climáticas según las posiciones de los planetas a 360° (un año de la tierra serán 360° + 5°. Es decir, un ciclo completo y 5 días más)
	 * NOTA:
	 * Un año para Ferengi son 360 días. Para Betasoide, 120. Y para Vulcano, 72.
	 * Año es lo que tarda en dar la vuelta al sol sobre su órbita.
	 * El cálculo lo hago sobre los días terrestres (365 días por año)
	 */
	public List<WeatherReport> getCalculations(int dias){
		//Avance diario Ferengi (un grado por día)
		int avanceFerengi = 0;
		//Avance diario Betasoide (3 grados por día)
		int avanceBetasoide = 0;
		//Avance diario Vulcano (5 grados por día, pero inverso)
		int avanceVulcano = gradosCiclo;
		
		List<WeatherReport>wrs = new ArrayList<WeatherReport>();
		
		for(int i = 1; i <= dias; i++){
			if(dias % 360 == 0){
				avanceFerengi = 0;
				avanceBetasoide = 0;
				avanceVulcano = gradosCiclo;
			}
			//Objeto reporte de clima. El estado por defecto es clima normal
			WeatherReport wr = new WeatherReport();
			wr.setDia(i);
			wr.setClima("Normal");
			
			//Calculos del grado de avance de los planetas por día (se suponde que el día es la hora cero de ese día)
			if(avanceFerengi == gradosCiclo)
				avanceFerengi = 0;
			avanceFerengi = avanceFerengi + 1;
			
			if(avanceBetasoide == gradosCiclo)
				avanceBetasoide = 0;
			avanceBetasoide = avanceBetasoide + 3;
			
			avanceVulcano = avanceVulcano - 5;
			if(avanceVulcano == 0)
				avanceVulcano = gradosCiclo;
			
			//La posición en grados del día anterior
			int avanceFerengiAnterior = avanceFerengi - 1;
			int avanceVulcanoAnterior = avanceVulcano + 5;
			int avanceBetasoideAnterior = avanceBetasoide - 3;
			
			//Calculo de la posición del planeta al día actual y la posición que tenían el día anterior (justo antes de la hora cero del día actual)
			double x1 = Math.sin(Math.toRadians(avanceFerengi))*distanciaFerengi;
			double y1 = Math.cos(Math.toRadians(avanceFerengi))*distanciaFerengi;
			double x1Anterior = Math.sin(Math.toRadians(avanceFerengiAnterior))*distanciaFerengi;
			double y1Anterior = Math.cos(Math.toRadians(avanceFerengiAnterior))*distanciaFerengi;
			
			double x2 = Math.sin(Math.toRadians(avanceVulcano))*distanciaVulcano;
			double y2 = Math.cos(Math.toRadians(avanceVulcano))*distanciaVulcano;
			double x2Anterior = Math.sin(Math.toRadians(avanceVulcanoAnterior))*distanciaVulcano;
			double y2Anterior = Math.cos(Math.toRadians(avanceVulcanoAnterior))*distanciaVulcano;
			
			double x3 = Math.sin(Math.toRadians(avanceBetasoide))*distanciaBetasoide;
			double y3 = Math.cos(Math.toRadians(avanceBetasoide))*distanciaBetasoide;
			double x3Anterior = Math.sin(Math.toRadians(avanceBetasoideAnterior))*distanciaBetasoide;
			double y3Anterior = Math.cos(Math.toRadians(avanceBetasoideAnterior))*distanciaBetasoide;
			
			
			//Calcula si es un día de sequía (alineados los planetas con el sol)}
			//NOTA: podría calcularse con la velocidad angular y analizar en que ángulos se cruzan, pero el resultado es el mismo y esto es mucho más simple de programar.
			if((avanceFerengi == avanceVulcano && avanceFerengi == avanceBetasoide) ||
				(avanceFerengi == avanceVulcano && (avanceFerengi == avanceBetasoide + 180 || avanceFerengi == avanceBetasoide - 180)) ||
				((avanceFerengi == avanceVulcano + 180 || avanceFerengi == avanceVulcano - 180) && avanceFerengi == avanceBetasoide) || 
				((avanceFerengi == avanceVulcano + 180 || avanceFerengi == avanceVulcano - 180) && (avanceFerengi == avanceBetasoide + 180 || avanceFerengi == avanceBetasoide - 180))){
				wr.setClima("Sequía");
			}else{
				//Calcula si es un perído óptimo de temperatura y presión (planetas alineados entre si pero no con el sol)
				
				//Calculo las pendientes de cada segmento (Ferengi-Betasoide[1] vs Ferengi-Vulcano[2]) de hoy y el día anterior.
				//Si la diferencia entre la pendiente actual y la del día anterior cambian de signo, es porque se cruzaron, lo que lo hace un día óptimo (si no pasa por el sol - (0,0))
				double m1Ant = (y3Anterior - y1Anterior)/(x3Anterior - x1Anterior);
				double m2Ant = (y2Anterior - y1Anterior)/(x2Anterior - x1Anterior);
				double m1 = (y3 - y1)/(x3 - x1);
				double m2 = (y2 - y1)/(x2 - x1);
				
				
				if((m1Ant - m2Ant) > 0 && ((m1 - m2) < 0) || 
				   (m1Ant - m2Ant) < 0 && ((m1 - m2) > 0) ||
				   (m1Ant - m2Ant) == 0 && ((m1 - m2) == 0)){
					wr.setClima("Óptimo");
				}else{
					//Calcula si es un período de lluvia (los planetas forman un trángulo encerrando al sol)
					//Punto central: (0,0)
					//Triángulo ABC
					double abc = (x1 - x3) * (y2 - y3) - (y1 - y3) * (x2 - x3);
					//Triángulo ABP
					double abp = (x1 - 0) * (y2 - 0) - (y1 - 0) * (x2 - 0);
					//Triángulo BCP
					double bcp = (0 - x3) * (y2 - y3) - (0 - y3) * (x2 - x3);
					//Triángulo ACP
					double acp = (x1 - x3) * (0 - y3) - (y1 - y3) * (0 - x3);
					
					//Calcula la distancia de los puntos para verificar si están alineados
					//(x2-x1)/(x3-x2) =? (y2-y1)/(y3-y2)					
					if(abc > 0 && abp > 0 && acp > 0 && bcp > 0)
						wr.setClima("Lluvia");
					else if(abc < 0 && abp < 0 && acp < 0 && bcp < 0)
						wr.setClima("Lluvia");
				}
			}
			//Guarda el período en el array
			wrs.add(wr);
		}
		return wrs;
	}
}
