package mx.edu.uacm.revistaAcercate.service;

import java.util.List;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.dominio.Autor;

public interface AutorService {
	
	/**
	  * Method to get a author
	  * @return Object Author
	  */
		
	  Autor registrarAutor(Autor autor) throws AplicacionExcepcion;

	  List<Autor> obtenerAutores();

}
