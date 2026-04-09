package mx.edu.uacm.revistaAcercate.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;

public interface ArticuloService {

    /**
     * Registro completo de artículo
     */
    Articulo registrarArticulo(
            Articulo articulo,
            MultipartFile archivo,
            List<MultipartFile> imagenes,
            Long autorPrincipalId,
            List<Long> autoresSecundariosIds
    ) throws AplicacionExcepcion;
    
    public Articulo actualizarArticulo(
            Long id,
            Articulo datos,
            MultipartFile archivo,
            List<MultipartFile> imagenes,
            Long autorPrincipalId,
            List<Long> autoresSecundariosIds,
            List<String> imagenesEliminar);

    /**
     * Obtener todos los artículos
     */
    List<Articulo> obtenerArticulos();

    /**
     * Eliminar artículo
     */
    void eliminarArticulo(Long id);

    /**
     * Obtener por ID (para edición)
     */
    Articulo obtenerPorId(Long id);
}