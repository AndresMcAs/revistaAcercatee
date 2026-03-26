package mx.edu.uacm.revistaAcercate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // Buscar por título (opcional)
    Articulo findByTitulo(String titulo);

}