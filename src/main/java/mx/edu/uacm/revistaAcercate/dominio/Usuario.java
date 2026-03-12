package mx.edu.uacm.revistaAcercate.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


/**
 * 
 * @author Andres Mendoza 
 *
 */
@Entity
@Data
public class Usuario  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nombre;
  private String apellidoPat;
  private String apellidoMat;
  private String correo;
  private String especialidad;
  private String gradoAcademico;
 
  private String telefono;
  private String genero;
  private String rol;
  private String contrasenia;
 

 

  
}
