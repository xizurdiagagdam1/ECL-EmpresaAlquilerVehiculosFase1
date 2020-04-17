package alquileres.modelo;

/**
 * Representa a un vehículo en alquiler De esta clase no se crearán instancias
 * 
 * De un vehículo se conoce su matrícula, marca, modelo y el precio a pagar
 * por día de alquiler
 * 
 * Para todo vehículo se puede calcular su coste de alquiler que depende del
 * nº de días que se alquile (llamaremos a esta operación
 * calcularPrecioAlquiler() )
 * 
 * Dos vehículos pueden compararse por su matrícula (es su orden natural)
 * 
 * Dos vehículos son iguales si además de pertenecer a la misma clase tienen
 * la misma matrícula
 * 
 */
public class Vehiculo {
	private String matricula;
	private String marca;
	private String modelo;
	private double precioDia;

	/**
	 * Constructor
	 */
	public Vehiculo(String matricula, String marca, String modelo, double precioDia) {
		this.matricula = matricula.toUpperCase();
		this.marca = marca.toUpperCase();
		this.modelo = modelo.toUpperCase();
		this.precioDia = precioDia;

	}

	public String getMatricula() {
		return matricula;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public double getPrecioDia() {
		return precioDia;
	}

	public double calcularPrecioAlquiler(int dias) {
		return dias * precioDia;
	}

	/**
	 * Redefinición de hashCode()
	 * 
	 */

	public int compareTo(Vehiculo obj) {
		return this.getMatricula().compareTo(obj.getMatricula());
	}

	public boolean equals(Vehiculo obj) {
		if (obj.getClass() == this.getClass() & this.getMatricula().compareTo(obj.getMatricula()) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return matricula.hashCode() * 13;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.getClass()).append("/n");
		sb.append("Matricula : ").append(matricula).append("  |  ");
		sb.append("Marca: ").append(marca).append("  |  ");
		sb.append("Modelo: ").append(modelo).append("\n");

		return sb.toString();
	}

}