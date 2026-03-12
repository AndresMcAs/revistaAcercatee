package mx.edu.uacm.revistaAcercate;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**  
 * Registro de las vistas de los controladores
 * @author Andres Mendoza 
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
  /**
   * registro de las vistas 
   */
  public void addViewControllers(ViewControllerRegistry registry) {
	  
     registry.addViewController("/").setViewName("index");
     registry.addViewController("/registroArticulos").setViewName("registro_articulos");
     registry.addViewController("/registroUsuarios").setViewName("registro_usuarios");
     registry.addViewController("/registroAutores").setViewName("registro_autores");
     registry.addViewController("/menuRevista").setViewName("menu_revistas");
     registry.addViewController("/menuRevision").setViewName("menu_revsion");
     registry.addViewController("/menuArticulos").setViewName("menu_articulos");
     registry.addViewController("/nuevaRevista").setViewName("nueva_revista");
     registry.addViewController("/asignacionRevision").setViewName("asignacion_revision");
     registry.addViewController("/login").setViewName("login");
    
  }

}
