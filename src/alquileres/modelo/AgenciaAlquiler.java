package alquileres.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 */
public class AgenciaAlquiler {
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.nombre = nombre;
		this.flota = new ArrayList<>();
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo vehiculo) {
		if (!flota.contains(vehiculo)) {
			flota.add(vehiculo);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo correspondiente
	 * 
	 * Formato de la línea: C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después de
	 * cada dato
	 */
	private Vehiculo obtenerVehiculo(String linea) {

		linea.trim();
		String[] datos = linea.split(",");
		if (datos[0].equalsIgnoreCase("C")) {
			Coche coche = new Coche(datos[1], datos[2], datos[3], Double.parseDouble(datos[4]),
					Integer.parseInt(datos[5]));
			return coche;
		}
		if (datos[0].equalsIgnoreCase("F")) {
			Furgoneta furgoneta = new Furgoneta(datos[1], datos[2], datos[3], Double.parseDouble(datos[4]),
					Double.parseDouble(datos[5]));
			return furgoneta;
		}
		return null;
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos de la
	 * flota de vehículos
	 */
	public void cargarFlota() {
		String[] datos = Utilidades.obtenerLineasDatos();
		String error = null;
		try {
			for (String linea : datos) {
				error = linea;
				Vehiculo vehiculo = obtenerVehiculo(linea);
				this.addVehiculo(vehiculo);

			}
		} catch (Exception e) {
			System.out.println(error);
		}

	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Vehiculos en alquiler de la agencia ").append(nombre).append("/n");
		sb.append("Total vehiculos: ").append(flota.size()).append("/n/n");

		for (int i = 0; i < flota.size(); i++) {
			sb.append(flota.get(i).toString()).append("/n/n");
			sb.append("-------------------------------------------------");
		}

		return sb.toString();
	}

	/**
	 * Busca todos los coches de la agencia Devuelve un String con esta información
	 * y lo que costaría alquilar cada coche el nº de días indicado *
	 * 
	 */
	public String buscarCoches(int dias) {

		StringBuilder sb = new StringBuilder();

		sb.append("Coches de alquiler en la agencia ").append("/n").append("/n");

		List<Vehiculo> coches = new ArrayList<>();
		for (int i = 0; i < flota.size(); i++) {
			if (flota.get(i) instanceof Coche) {
				coches.add(flota.get(i));
			}
		}

		for (int i = 0; i < coches.size(); i++) {
			sb.append(coches.get(i).toString()).append("/n");
			sb.append("Precio: ").append(coches.get(i).getPrecioDia() + coches.get(i).calcularPrecioAlquiler(dias))
					.append("/n/n");
			sb.append("-------------------------------------------------");
		}

		return sb.toString();

	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * 
	 */
	public List<Coche> cochesOrdenadosMatricula() {

		List<Coche> retornado = new ArrayList<>();

		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo vehiculo = it.next();

			if (vehiculo instanceof Coche) {
				Coche coche = (Coche) vehiculo;
				if (coche.getPlazas() > 4) {
					if (retornado.size() == 0) {
						retornado.add(coche);
					} else {

						for (int a = 0; a < retornado.size(); a++) {
							if (retornado.get(a).compareTo(vehiculo) > 0) {
								retornado.add(a, (Coche) vehiculo);
							}
						}
					}
				}
			}
		}
		return retornado;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de mayor a menor
	 * volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {

		List<Furgoneta> retornado = new ArrayList<>();

		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo vehiculo = it.next();

			if (vehiculo instanceof Furgoneta) {
				Furgoneta furgoneta = (Furgoneta) vehiculo;
				if (retornado.size() == 0) {
					retornado.add(furgoneta);
				} else {
					for (int a = 0; a < retornado.size(); a++) {
						if (retornado.get(a).getCarga() < furgoneta.getCarga()) {
							retornado.add(a, furgoneta);
						}
					}
				}
			}
		}
		return retornado;
	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden)
	 * de los modelos en cada marca como valor asociado
	 */
	public TreeMap<String, TreeSet<String>> marcasConModelos() {

		TreeMap<String, TreeSet<String>> retornado = new TreeMap<String, TreeSet<String>>();

		for (int a = 0; a < flota.size(); a++) {
			if (retornado.containsKey(flota.get(a).getMarca())) {
				retornado.get(flota.get(a).getMarca()).add(flota.get(a).getModelo());
			} else {
				TreeSet<String> modelos = new TreeSet<String>();
				modelos.add(flota.get(a).getModelo());
				retornado.put(flota.get(a).getMarca(), modelos);
			}
		}

		return retornado;
	}

}
