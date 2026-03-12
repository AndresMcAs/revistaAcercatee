package mx.edu.uacm.revistaAcercate.service;

import java.util.List;

import mx.edu.uacm.revistaAcercate.dominio.Usuario;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;


public interface UsuarioService {
  
  /**
  * Metodo para obtener al usuario 
  * @param correo
  * @param contrasenia
  * @return Objeto usuario
  */
  Usuario obtenerUsuarioPorCorreoYContrasenia(String correo, String contrasenia);
	
  Usuario registrarUsuario(Usuario usuario) throws AplicacionExcepcion;

  List<Usuario> obtenerUsuarios();
}
