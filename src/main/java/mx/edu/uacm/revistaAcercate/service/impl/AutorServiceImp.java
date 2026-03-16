package mx.edu.uacm.revistaAcercate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.repository.AutorRepository;
import mx.edu.uacm.revistaAcercate.service.AutorService;


@Service
@Slf4j
public class AutorServiceImp implements AutorService {
	
	@Autowired
	private AutorRepository AutorRepository;
	
	public Autor obtenerUsuarioPorNombre(String nombre) {
		
		if(log.isDebugEnabled())
			log.debug("> Entrando a UsuarioServiceImpl.obtenerUsuarioPorCorreoYContrasenia()");
		
		Autor usuarioRecuperado = AutorRepository.findByNombre(nombre);
		
		log.debug("usuario recuperado: " + usuarioRecuperado);
		
		if(usuarioRecuperado != null ) {
			
			return usuarioRecuperado;
		} else {
			
			return null;
		}

	}
	
	@Override
	public Autor registrarAutor(Autor Autor) throws AplicacionExcepcion {
		if (log.isDebugEnabled())
			log.debug(" > Entrando a AutorService.registrarAutor");
		
		 Autor AutorGuardado = null;
		 
		 try {
			
			
			AutorGuardado = AutorRepository.save(Autor);
		
		 } catch (DataAccessException e) {
			 log.error(e.getMessage());
             throw new AplicacionExcepcion("Error al guardar el registro");		
		 
		 }
		 
		return AutorGuardado;
	}

	@Override
	public List<Autor> obtenerAutores() {
		
		return (List<Autor>)AutorRepository.findAll();
	}

}
