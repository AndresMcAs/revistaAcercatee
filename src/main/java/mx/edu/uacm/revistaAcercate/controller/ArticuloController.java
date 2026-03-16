package mx.edu.uacm.revistaAcercate.controller;

import java.util.List;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controller of articles 
 * 
 * @author AndreMc
 *
 */
@Controller
@RequestMapping("/usuario")
@Slf4j
public class ArticuloController {

	private ArticuloService articuloService;

	public ArticuloController(ServletContext servletContext) {
	}

	/**
	 * registry of articles
	 * 
	 * @param model
	 * @param articulo
	 * @return
	 */
	@PostMapping("/registro")
	public String registrarUsuario(Model model, Articulo articulo) {

		String registrar;

		log.debug("> Entrando a ArticuloController.registrarArticulo");
		log.debug("Usuario {}", articulo);

		if (articulo.getTitulo() != null && articulo.getArchivoWord() != null) {

			try {

				Articulo ArticuloGuardado = articuloService.registrarArticulo(articulo);

				if (ArticuloGuardado != null && ArticuloGuardado.getId() != null) {

					model.addAttribute("mensajeExitoso", "Registro exitoso! " + articulo.getTitulo());

				}

			} catch (AplicacionExcepcion e) {

				log.error(e.getMessage());
				model.addAttribute("mensajeError", e.getMessage());

			}

			registrar = "registro_articulos :: #modalMensaje";

		} else {

			registrar = "redirect:/registro_articulos";

		}

		return registrar;
	}

	/**
	 * List of articles
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/buscar")
	public String buscar(Model model) {
		if (log.isDebugEnabled())
			log.debug("entrando a buscar arrticulos  ");
		List<Articulo> articulos = articuloService.obtenerArticulos();
		model.addAttribute("articulos", articulos);
		return "menu_articulos";

	}

}
