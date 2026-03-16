package mx.edu.uacm.revistaAcercate.controller;

import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Usuario;
import mx.edu.uacm.revistaAcercate.dominio.Autor;

import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.service.UsuarioService;
import mx.edu.uacm.revistaAcercate.service.AutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controlador de usuarios
 * @author Andres Mendoza 
 *
 */
@Controller
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {
	
  @Autowired
  private UsuarioService usuarioService;
  @Autowired
  private AutorService autorService;
  @Autowired
  private HttpSession httpSession;
  private String registrar;
  private String paginaInicio;
  private ServletContext servletContext;
	
  public UsuarioController(ServletContext servletContext) {
    this.servletContext = servletContext;
  }
  
  /**
   * logueo de usuarios 
   * @param correo
   * @param password
   * @param model
   * @return
   */
  @PostMapping("/login")
  public String iniciarSesion(@RequestParam("correo") String correo,
                       @RequestParam("contrasenia") String password,
                       Model model) {
	  
    if (log.isDebugEnabled())
      log.debug("> Entrando al metodo iniciarSesion <");
    Usuario usuario = usuarioService.obtenerUsuarioPorCorreoYContrasenia(correo, password);
    
    if (usuario != null) {
      httpSession.setAttribute("usuarioLogueado", usuario);
      
        paginaInicio = "redirect:/home";
        
      
    } else {
      servletContext.setAttribute("errorMensaje", "Usuario/Contrasenia no validos");
      paginaInicio = "redirect:/login";

    }
    return paginaInicio;
  }

  @GetMapping("/logout")
  public String logout() {
    httpSession.removeAttribute("usuarioLogueado");
    return "redirect:/";
  }
	

  @GetMapping("/initlogin")
  public String iniciarLogin() {
    servletContext.removeAttribute("errorMensaje");
    return "redirect:/login";
  }
  
  /**
   * registro de usuarios 
   * @param model
   * @param usuario
   * @return
   */
  @PostMapping("/registro")
  public String registrarUsuario(Model model, Usuario usuario) {

      String registrar;

      log.debug("> Entrando a usuarioController.registrarUsuario");
      log.debug("Usuario {}", usuario);

      if (usuario.getNombre() != null && usuario.getCorreo() != null) {

          try {

              Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);

              if (usuarioGuardado != null && usuarioGuardado.getId() != null) {

                  model.addAttribute(
                          "mensajeExitoso",
                          "Registro exitoso! " + usuario.getNombre()
                  );

              }

          } catch (AplicacionExcepcion e) {

              log.error(e.getMessage());
              model.addAttribute("mensajeError", e.getMessage());

          }

          registrar = "registro_usuarios :: #modalMensaje";

      } else {

          registrar = "redirect:/registro_usuarios";

      }

      return registrar;
  }
  
  /**
   * lista de usuarios 
   * @param model
   * @return
   */
  @GetMapping("/buscar")
  public String buscar(Model model) {
	if (log.isDebugEnabled())
		log.debug("entrando a buscar usuarios ");
	List<Usuario> usuarios = usuarioService.obtenerUsuarios();
	model.addAttribute("usuarios", usuarios);
	return "home-revision";
  // evaluacion 
	  
  }
  
  
  
  /* registro de autores */
  
  /**
   * registro de autores 
   * @param model
   * @param autor
   * @return
   */
  @PostMapping("/registroAutor")
  public String registrarAutor(Model model, Autor autor) {

  

      log.debug("> Entrando a usuarioController.registrarAutor");
      log.debug("autor {}", autor);

      if (autor.getNombre() != null && autor.getCorreo() != null) {

          try {

              Autor usuarioGuardado = autorService.registrarAutor(autor);

              if (usuarioGuardado != null && usuarioGuardado.getId() != null) {

                  model.addAttribute(
                          "mensajeExitoso",
                          "Registro exitoso! " + autor.getNombre()
                  );

              }

          } catch (AplicacionExcepcion e) {

              log.error(e.getMessage());
              model.addAttribute("mensajeError", e.getMessage());

          }

          registrar = "registro_autores :: #modalMensaje";

      } else {

          registrar = "redirect:/registro_autores";

      }

      return registrar;
  }

}
