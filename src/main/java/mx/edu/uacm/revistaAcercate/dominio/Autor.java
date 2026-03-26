package mx.edu.uacm.revistaAcercate.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Data
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidoPat;
    private String apellidoMat;
    private String correo;
    private String especialidad;
    private String gradoAcademico;
   
    private String institucion;
    private String telefono;
    private String genero;
    
    public Autor() {
    	
    }

}