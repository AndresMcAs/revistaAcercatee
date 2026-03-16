package mx.edu.uacm.revistaAcercate.service;

import java.util.List;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;

public interface ArticuloService {
	
	/**
	  * Method of oftener a article
	  * @return Object articles
	  */
		
	  Articulo registrarArticulo(Articulo articulo) throws AplicacionExcepcion;

	  List<Articulo> obtenerArticulos();

}
