package mx.edu.uacm.revistaAcercate.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;



	@Entity
	@Data
	public class Articulo {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String titulo;

	    @Column(length = 2000)
	    private String descripcion;

	    private LocalDate fechaRegistro;

	    private String estatus;

	    private String version;

	    private Integer numeroCaracteres;

	    private String tema;

	    private String seccion;

	    private String archivoWord;

	    @ElementCollection
	    private List<String> imagenes;

	    @ManyToMany
	    @JoinColumn(name="id", foreignKey=  @ForeignKey(name = "autor_FK"))
	    private List<Autor> autores;

	    public Articulo () {
	    	
	    }
	    // getters y setters
	}


