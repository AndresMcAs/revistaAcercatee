package mx.edu.uacm.revistaAcercate.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;
import mx.edu.uacm.revistaAcercate.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/articulo")
@RequiredArgsConstructor 
public class ArticuloController {

    private final ArticuloService articuloService;
    private final AutorService autorService;
   
    @PostMapping("/registro")
    public String registrarArticulo(
            Model model,
            Articulo articulo,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "imagenesFiles", required = false) List<MultipartFile> imagenes,
            @RequestParam("autorPrincipal") Long autorPrincipal,
            @RequestParam(value = "autoresSecundarios", required = false) String[] autoresSecundarios
    ) {

        try {

            List<Long> ids = new ArrayList<>();

            if (autoresSecundarios != null) {
                for (String id : autoresSecundarios) {
                    if (id != null && !id.isEmpty()) {
                        ids.add(Long.parseLong(id));
                    }
                }
            }

            articuloService.registrarArticulo(
                    articulo,
                    archivo,
                    imagenes,
                    autorPrincipal,
                    ids
            );

            model.addAttribute("mensajeExitoso", "Artículo registrado correctamente");

        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return "registro_articulos :: #modalMensaje";
    }
    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {

        List<Autor> autores = autorService.obtenerAutores();
        model.addAttribute("autores", autores);

        return "registro_articulos";
    }
    @GetMapping
    public String listarArticulos(Model model) {
        List<Articulo> lista = articuloService.obtenerArticulos();
        model.addAttribute("articulos", lista);
        return "menu_articulos";
    }

    /**
     * ELIMINAR ARTÍCULO
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model) {
    	 System.out.println("id: " + id);
        try {

            articuloService.eliminarArticulo(id);
            model.addAttribute("mensajeExitoso", "Artículo eliminado correctamente");

        } catch (Exception e) {

            model.addAttribute("mensajeError", "Error al eliminar");
        }

        return "menu_articulos :: #modalMensaje";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Articulo articulo = articuloService.obtenerPorId(id);
        List<Autor> autores = autorService.obtenerAutores();

        model.addAttribute("articulo", articulo);
        model.addAttribute("autores", autores);

        return "editar_articulo";
    }
    
    @PostMapping("/editar/{id}")
    public String actualizarArticulo(
            @PathVariable Long id,
            Articulo articulo,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "imagenesFiles", required = false) List<MultipartFile> imagenes,
            @RequestParam("autorPrincipal") Long autorPrincipal,
            @RequestParam(value = "autoresSecundarios", required = false) String[] autoresSecundarios,
            @RequestParam(value = "imagenesEliminar", required = false) List<String> imagenesEliminar,
            Model model
    ) {

        try {

            // 🔥 CONVERSIÓN SEGURA
            List<Long> ids = new ArrayList<>();

            if (autoresSecundarios != null) {
                for (String a : autoresSecundarios) {
                    if (a != null && !a.isEmpty()) {
                        ids.add(Long.parseLong(a));
                    }
                }
            }

            articuloService.actualizarArticulo(
                    id,
                    articulo,
                    archivo,
                    imagenes,
                    autorPrincipal,
                    ids,
                    imagenesEliminar
            );

            return "redirect:/articulo";

        } catch (Exception e) {

            model.addAttribute("mensajeError", e.getMessage());
            return "editar_articulo";
        }
    }

}