package mx.edu.uacm.revistaAcercate.repository;

import org.springframework.data.repository.CrudRepository;


import mx.edu.uacm.revistaAcercate.dominio.Articulo;

public interface ArticuloRepository extends CrudRepository<Articulo, Long> {
	
	Articulo findByNombre(String nombre);
	
	public Long countById(Long id);

	
}
