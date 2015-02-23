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
	 * Carga inicial del per�odo seg�n la cantidad de d�as pasados por par�metro
	 * Elimina la carga actual de la tabla
	 * @param daysQuantity: cantidad de d�as que van a crearse
	 */
	public void populateDB(int daysQuantity){
		//Borra la tabla y realiza los n-inserts
		WeatherReportBusiness wrb = new WeatherReportBusiness();
		try{
			wrb.saveWeatherCondition(getCalculations(3650));
		}catch(SQLException ex){
			System.err.print("-->Error al borrar, persistir o intentar conectarse a la DB");
		}catch(Exception ex){
			System.err.print("-->No pudo realizarse el c�lculo de las condiciones clim�ticas");
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
		//C�lculos
//		List<WeatherReport>wrs = wrj.getCalculations(3650);
//		for(WeatherReport wr : wrs){
//			System.out.println(wr.toString());
//		}
		
	}
	
	
	
	/**
	 * Realiza el c�lculo de condiciones clim�ticas seg�n las posiciones de los planetas a 360� (un a�o de la tierra ser�n 360� + 5�. Es decir, un ciclo completo y 5 d�as m�s)
	 * NOTA:
	 * Un a�o para Ferengi son 360 d�as. Para Betasoide, 120. Y para Vulcano, 72.
	 * A�o es lo que tarda en dar la vuelta al sol sobre su �rbita.
	 * El c�lculo lo hago sobre los d�as terrestres (365 d�as por a�o)
	 */
	public List<WeatherReport> getCalculations(int dias){
		//Avance diario Ferengi (un grado por d�a)
		int avanceFerengi = 0;
		//Avance diario Betasoide (3 grados por d�a)
		int avanceBetasoide = 0;
		//Avance diario Vulcano (5 grados por d�a, pero inverso)
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
			
			//Calculos del grado de avance de los planetas por d�a (se suponde que el d�a es la hora cero de ese d�a)
			if(avanceFerengi == gradosCiclo)
				avanceFerengi = 0;
			avanceFerengi = avanceFerengi + 1;
			
			if(avanceBetasoide == gradosCiclo)
				avanceBetasoide = 0;
			avanceBetasoide = avanceBetasoide + 3;
			
			avanceVulcano = avanceVulcano - 5;
			if(avanceVulcano == 0)
				avanceVulcano = gradosCiclo;
			
			//La posici�n en grados del d�a anterior
			int avanceFerengiAnterior = avanceFerengi - 1;
			int avanceVulcanoAnterior = avanceVulcano + 5;
			int avanceBetasoideAnterior = avanceBetasoide - 3;
			
			//Calculo de la posici�n del planeta al d�a actual y la posici�n que ten�an el d�a anterior (justo antes de la hora cero del d�a actual)
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
			
			
			//Calcula si es un d�a de sequ�a (alineados los planetas con el sol)}
			//NOTA: podr�a calcularse con la velocidad angular y analizar en que �ngulos se cruzan, pero el resultado es el mismo y esto es mucho m�s simple de programar.
			if((avanceFerengi == avanceVulcano && avanceFerengi == avanceBetasoide) ||
				(avanceFerengi == avanceVulcano && (avanceFerengi == avanceBetasoide + 180 || avanceFerengi == avanceBetasoide - 180)) ||
				((avanceFerengi == avanceVulcano + 180 || avanceFerengi == avanceVulcano - 180) && avanceFerengi == avanceBetasoide) || 
				((avanceFerengi == avanceVulcano + 180 || avanceFerengi == avanceVulcano - 180) && (avanceFerengi == avanceBetasoide + 180 || avanceFerengi == avanceBetasoide - 180))){
				wr.setClima("Sequ�a");
			}else{
				//Calcula si es un per�do �ptimo de temperatura y presi�n (planetas alineados entre si pero no con el sol)
				
				//Calculo las pendientes de cada segmento (Ferengi-Betasoide[1] vs Ferengi-Vulcano[2]) de hoy y el d�a anterior.
				//Si la diferencia entre la pendiente actual y la del d�a anterior cambian de signo, es porque se cruzaron, lo que lo hace un d�a �ptimo (si no pasa por el sol - (0,0))
				double m1Ant = (y3Anterior - y1Anterior)/(x3Anterior - x1Anterior);
				double m2Ant = (y2Anterior - y1Anterior)/(x2Anterior - x1Anterior);
				double m1 = (y3 - y1)/(x3 - x1);
				double m2 = (y2 - y1)/(x2 - x1);
				
				
				if((m1Ant - m2Ant) > 0 && ((m1 - m2) < 0) || 
				   (m1Ant - m2Ant) < 0 && ((m1 - m2) > 0) ||
				   (m1Ant - m2Ant) == 0 && ((m1 - m2) == 0)){
					wr.setClima("�ptimo");
				}else{
					//Calcula si es un per�odo de lluvia (los planetas forman un tr�ngulo encerrando al sol)
					//Punto central: (0,0)
					//Tri�ngulo ABC
					double abc = (x1 - x3) * (y2 - y3) - (y1 - y3) * (x2 - x3);
					//Tri�ngulo ABP
					double abp = (x1 - 0) * (y2 - 0) - (y1 - 0) * (x2 - 0);
					//Tri�ngulo BCP
					double bcp = (0 - x3) * (y2 - y3) - (0 - y3) * (x2 - x3);
					//Tri�ngulo ACP
					double acp = (x1 - x3) * (0 - y3) - (y1 - y3) * (0 - x3);
					
					//Calcula la distancia de los puntos para verificar si est�n alineados
					//(x2-x1)/(x3-x2) =? (y2-y1)/(y3-y2)					
					if(abc > 0 && abp > 0 && acp > 0 && bcp > 0)
						wr.setClima("Lluvia");
					else if(abc < 0 && abp < 0 && acp < 0 && bcp < 0)
						wr.setClima("Lluvia");
				}
			}
			//Guarda el per�odo en el array
			wrs.add(wr);
		}
		return wrs;
	}
}
