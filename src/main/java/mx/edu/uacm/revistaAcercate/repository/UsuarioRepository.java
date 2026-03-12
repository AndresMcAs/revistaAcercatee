package mx.edu.uacm.revistaAcercate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import mx.edu.uacm.revistaAcercate.dominio.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	Usuario findByCorreoAndContrasenia(String correo, String contrasenia);

	Usuario findByCorreo(String correo);
	
	@Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
	public Usuario getUsuariorBycorreo(@Param("correo") String correo);
	
	
	public Long countById(Long id);

	
}
