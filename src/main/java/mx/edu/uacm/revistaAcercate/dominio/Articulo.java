package mx.edu.uacm.revistaAcercate.dominio;

import jakarta.persistence.*;
import lombok.Data;

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
    private Integer numero;
    private String tema;
    private String seccion;
    private String archivoWord;
    @ElementCollection
    private List<String> imagenes;

    @ManyToOne
    @JoinColumn(name = "autor_principal_id")
    private Autor autorPrincipal;

    @ManyToMany
    @JoinTable(
        name = "articulo_autores",
        joinColumns = @JoinColumn(name = "articulo_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autoresSecundarios;

}