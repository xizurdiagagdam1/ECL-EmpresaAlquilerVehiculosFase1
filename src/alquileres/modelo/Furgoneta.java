package alquileres.modelo;

/**
 * Una furgoneta es un vehículo que añade la característica del volumen de
 * carga (valor de tipo double)
 * 
 * El coste de alquiler de una furgoneta no solo depende del nº de días de
 * alquiler Tendrá un incremento que dependerá de su volumen de carga: hasta 5
 * m3 exclusive ( metros cúbicos) de volumen el incremento sobre el precio
 * final será de 10€, entre 5 y 10 (inclusive) el incremento sobre el precio
 * final será de 15€, si volumen > 10 el incremento sobre el precio final
 * será de 25€
 * 
 */
public class Furgoneta extends Vehiculo {

	private double carga;

	public Furgoneta(String matricula, String marca, String modelo, double precioDia, double carga) {
		super(matricula, marca, modelo, precioDia);
		this.carga = carga;
	}

	public double getCarga() {
		return carga;
	}

	public int costeAdicional() {
		if (carga < 5) {
			return 10;
		}
		if (carga > 5 && carga <= 10) {
			return 15;
		}
		return 25;
	}

}
