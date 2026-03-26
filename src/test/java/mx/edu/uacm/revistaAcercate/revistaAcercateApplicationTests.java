package mx.edu.uacm.revistaAcercate;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.service.AutorService;

@SpringBootTest
class revistaAcercateApplicationTests {
   public AutorService autor;

   
	@Test
	void contextLoads() {
	
   if(autor==null) {
	   System.out.println("lista vacia");
   }		
    List<Autor> autores= autor.obtenerAutores();
	if(autores!=null) {
	
	long i=autores.size();
	System.out.print(i);
	} else {
		System.out.println("lista vacia ");
	}
	}
	


}
