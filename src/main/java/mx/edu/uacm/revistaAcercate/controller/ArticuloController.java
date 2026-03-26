package mx.edu.uacm.revistaAcercate.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;
import mx.edu.uacm.revistaAcercate.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/articulo")
@Slf4j
@RequiredArgsConstructor 
public class ArticuloController {

    private final ArticuloService articuloService;
    private final AutorService autorService;
    /**
     * REGISTRO DE ARTÍCULOS
     */
    @PostMapping("/registro")
    public String registrarArticulo(
            Model model,
            Articulo articulo,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "imagenes", required = false) List<MultipartFile> imagenes,
            @RequestParam(value = "autorPrincipal", required = false) Long autorPrincipal,
            @RequestParam(value = "autoresSecundarios", required = false) List<Long> autoresSecundarios
    ) {

        String vista;

        log.debug("> Entrando a ArticuloController.registrarArticulo");
        log.debug("Articulo {}", articulo);

        try {

            //  FECHA AUTOMÁTICA
            articulo.setFechaRegistro(LocalDate.now());

            // VALIDACIÓN BÁSICA
            if (articulo.getTitulo() == null || articulo.getTitulo().isEmpty()) {
                throw new AplicacionExcepcion("El título es obligatorio");
            }

           
            Articulo guardado = articuloService.registrarArticulo(
                    articulo,
                    archivo,
                    imagenes,
                    autorPrincipal,
                    autoresSecundarios
            );

            if (guardado != null && guardado.getId() != null) {
                model.addAttribute("mensajeExitoso", "Registro exitoso: " + articulo.getTitulo());
            }

            vista = "registro_articulos :: #modalMensaje";

        } catch (AplicacionExcepcion e) {

            log.error(e.getMessage());
            model.addAttribute("mensajeError", e.getMessage());
            vista = "registro_articulos :: #modalMensaje";
        }

        return vista;
    }

    /**
     * LISTAR ARTÍCULOS
     */
    @GetMapping("/buscar")
    public String buscar(Model model) {

        log.debug("Entrando a buscar artículos");

        List<Articulo> articulos = articuloService.obtenerArticulos();
        model.addAttribute("articulos", articulos);

        return "menu_articulos";
    }

    /**
     * ELIMINAR ARTÍCULO
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model) {

        try {

            articuloService.eliminarArticulo(id);
            model.addAttribute("mensajeExitoso", "Artículo eliminado correctamente");

        } catch (Exception e) {

            model.addAttribute("mensajeError", "Error al eliminar");
        }

        return "menu_articulos :: #modalMensaje";
    }

    /**
     * EDITAR (CARGAR DATOS)
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Articulo articulo = articuloService.obtenerPorId(id);
        model.addAttribute("articulo", articulo);

        return "registro_articulos";
    }
    
    /* Cargar datos de los autores*/
    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {

        List<Autor> autores = autorService.obtenerAutores(); 

        model.addAttribute("autores", autores);
        System.out.println("Autores: " + autores.size());

        return "registro_articulos";
    }

	

}