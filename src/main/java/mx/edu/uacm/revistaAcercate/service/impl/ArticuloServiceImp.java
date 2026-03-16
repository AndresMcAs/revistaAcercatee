package mx.edu.uacm.revistaAcercate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.repository.ArticuloRepository;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;


@Service
@Slf4j
public class ArticuloServiceImp implements ArticuloService {
	
	@Autowired
	private ArticuloRepository ArticuloRepository;
	
	public Articulo obtenerArticuloPorNombre(String nombre) {
		
		if(log.isDebugEnabled())
			log.debug("> Entrando a UsuarioServiceImpl.obtenerUsuarioPorCorreoYContrasenia()");
		
		Articulo articuloRecuperado = ArticuloRepository.findByNombre(nombre);
		
		log.debug("articulo recuperado: " + articuloRecuperado);
		
		if(articuloRecuperado != null ) {
			
			return articuloRecuperado;
		} else {
			
			return null;
		}

	}
	
	@Override
	public Articulo registrarArticulo(Articulo articulo) throws AplicacionExcepcion {
		if (log.isDebugEnabled())
			log.debug(" > Entrando a ArticuloService.registrarArticulo");
		
		 Articulo ArticuloGuardado = null;
		 
		 try {
			
			
			ArticuloGuardado = ArticuloRepository.save(articulo);
		
		 } catch (DataAccessException e) {
			 log.error(e.getMessage());
             throw new AplicacionExcepcion("Error al guardar el registro");		
		 
		 }
		 
		return ArticuloGuardado;
	}

	@Override
	public List<Articulo> obtenerArticulos() {
		
		return (List<Articulo>)ArticuloRepository.findAll();
	}

}
