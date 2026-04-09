package mx.edu.uacm.revistaAcercate.service.impl;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.repository.ArticuloRepository;
import mx.edu.uacm.revistaAcercate.repository.AutorRepository;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticuloServiceImp implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final AutorRepository autorRepository;

    @Override
    public Articulo registrarArticulo(
            Articulo articulo,
            MultipartFile archivo,
            List<MultipartFile> imagenes,
            Long autorPrincipalId,
            List<Long> autoresSecundariosIds) {

        try {

            // ====================
            // FECHA
            // ====================
            articulo.setFechaRegistro(LocalDate.now());

            // ====================
            // AUTOR PRINCIPAL
            // ====================
            Autor autor = autorRepository.findById(autorPrincipalId)
                    .orElseThrow(() -> new RuntimeException("Autor principal no encontrado"));

            articulo.setAutorPrincipal(autor);

         // AUTORES SECUNDARIOS
            List<Autor> listaAutores = new ArrayList<>();

            if (autoresSecundariosIds != null && !autoresSecundariosIds.isEmpty()) {

                List<Long> idsValidos = autoresSecundariosIds.stream()
                        .filter(id -> id != null && id > 0)
                        .distinct()
                        .toList();

                if (!idsValidos.isEmpty()) {

                    listaAutores = autorRepository.findAllById(idsValidos);

                    // evitar que se meta el principal
                    listaAutores = listaAutores.stream()
                            .filter(a -> !a.getId().equals(autorPrincipalId))
                            .toList();
                }
            }

            articulo.setAutoresSecundarios(listaAutores);
            
            // ====================
            // ARCHIVO WORD
            // ====================
            if (archivo != null && !archivo.isEmpty()) {

                String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                Path ruta = Paths.get("uploads").resolve(nombre);

                Files.createDirectories(ruta.getParent());
                Files.write(ruta, archivo.getBytes());

                articulo.setArchivoWord("/uploads/" + nombre);
            } else {
                throw new RuntimeException("Debes subir un archivo Word");
            }
            System.out.println("IDs recibidos: " + autoresSecundariosIds);
            System.out.println("IDs filtrados: " + listaAutores);
            // ====================
            // IMÁGENES (OPCIONAL)
            // ====================
            List<String> rutasImagenes = new ArrayList<>();

            if (imagenes != null && !imagenes.isEmpty()) {

                for (MultipartFile img : imagenes) {

                    if (!img.isEmpty()) {

                        String nombreImg = UUID.randomUUID() + "_" + img.getOriginalFilename();
                        Path rutaImg = Paths.get("uploads").resolve(nombreImg);

                        Files.createDirectories(rutaImg.getParent());
                        Files.write(rutaImg, img.getBytes());

                        rutasImagenes.add("/uploads/" + nombreImg);
                    }
                }
            }

            articulo.setImagenes(rutasImagenes);

            // ====================
            // GUARDAR
            // ====================
            return articuloRepository.save(articulo);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar artículo: " + e.getMessage());
        }
    }
    
    public Articulo actualizarArticulo(
            Long id,
            Articulo datos,
            MultipartFile archivo,
            List<MultipartFile> imagenes,
            Long autorPrincipalId,
            List<Long> autoresSecundariosIds,
            List<String> imagenesEliminar) {

        try {

            Articulo existente = articuloRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

            // ====================
            // DATOS BÁSICOS
            // ====================
            existente.setTitulo(datos.getTitulo());
            existente.setDescripcion(datos.getDescripcion());
            existente.setEstatus(datos.getEstatus());
            existente.setVersion(datos.getVersion());
            existente.setNumero(datos.getNumero());
            existente.setTema(datos.getTema());
            existente.setSeccion(datos.getSeccion());

            // ====================
            // AUTOR PRINCIPAL
            // ====================
            Autor autorPrincipal = autorRepository.findById(autorPrincipalId)
                    .orElseThrow(() -> new RuntimeException("Autor principal no encontrado"));

            existente.setAutorPrincipal(autorPrincipal);

            // ====================
            // AUTORES SECUNDARIOS
            // ====================
            List<Autor> listaAutores = new ArrayList<>();

            if (autoresSecundariosIds != null && !autoresSecundariosIds.isEmpty()) {

                List<Long> idsValidos = autoresSecundariosIds.stream()
                        .filter(x -> x != null && x > 0)
                        .distinct()
                        .toList();

                listaAutores = autorRepository.findAllById(idsValidos);

                // 🔥 evitar duplicar principal
                listaAutores = listaAutores.stream()
                        .filter(a -> !a.getId().equals(autorPrincipalId))
                        .toList();
            }

            existente.setAutoresSecundarios(listaAutores);

            // ====================
            // WORD (SOLO SI CAMBIA)
            // ====================
            if (archivo != null && !archivo.isEmpty()) {

                String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                Path ruta = Paths.get("uploads").resolve(nombre);

                Files.createDirectories(ruta.getParent());
                Files.write(ruta, archivo.getBytes());

                existente.setArchivoWord("/uploads/" + nombre);
            }

            // ====================
            // IMÁGENES EXISTENTES
            // ====================
            List<String> rutas = existente.getImagenes() != null
                    ? new ArrayList<>(existente.getImagenes())
                    : new ArrayList<>();

            // ====================
            // ELIMINAR IMÁGENES
            // ====================
            if (imagenesEliminar != null && !imagenesEliminar.isEmpty()) {

                rutas.removeAll(imagenesEliminar);

                // opcional: borrar del disco
                for (String rutaImg : imagenesEliminar) {
                    try {
                        Path path = Paths.get(rutaImg.replace("/uploads/", "uploads/"));
                        Files.deleteIfExists(path);
                    } catch (Exception ignored) {}
                }
            }

            // ====================
            // AGREGAR NUEVAS IMÁGENES
            // ====================
            if (imagenes != null && !imagenes.isEmpty()) {

                for (MultipartFile img : imagenes) {

                    if (!img.isEmpty()) {

                        String nombre = UUID.randomUUID() + "_" + img.getOriginalFilename();
                        Path rutaImg = Paths.get("uploads").resolve(nombre);

                        Files.createDirectories(rutaImg.getParent());
                        Files.write(rutaImg, img.getBytes());

                        rutas.add("/uploads/" + nombre);
                    }
                }
            }

            existente.setImagenes(rutas);

            // ====================
            // GUARDAR
            // ====================
            return articuloRepository.save(existente);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar artículo: " + e.getMessage());
        }
    }
    @Override
    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    // ========================
    //  ELIMINAR
    // ========================
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }

    // ========================
    //  OBTENER POR ID
    // ========================
    public Articulo obtenerPorId(Long id) {
        return articuloRepository.findById(id).orElse(null);
    }
    
    


}