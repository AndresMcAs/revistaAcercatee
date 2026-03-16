package mx.edu.uacm.revistaAcercate.repository;

import org.springframework.data.repository.CrudRepository;


import mx.edu.uacm.revistaAcercate.dominio.Autor;

public interface AutorRepository extends CrudRepository<Autor, Long> {
	
	Autor findByNombre(String nombre);
	
	public Long countById(Long id);

	
}
