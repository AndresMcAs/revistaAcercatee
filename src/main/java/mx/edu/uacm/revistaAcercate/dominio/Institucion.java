package mx.edu.uacm.revistaAcercate.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data 
public class Institucion {

	 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String nombre;
   private String direccion;
}
