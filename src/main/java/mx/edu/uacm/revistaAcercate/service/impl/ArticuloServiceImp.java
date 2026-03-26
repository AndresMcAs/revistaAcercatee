package mx.edu.uacm.revistaAcercate.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.edu.uacm.revistaAcercate.dominio.Articulo;
import mx.edu.uacm.revistaAcercate.dominio.Autor;
import mx.edu.uacm.revistaAcercate.error.AplicacionExcepcion;
import mx.edu.uacm.revistaAcercate.repository.ArticuloRepository;
import mx.edu.uacm.revistaAcercate.repository.AutorRepository;
import mx.edu.uacm.revistaAcercate.service.ArticuloService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticuloServiceImp implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final AutorRepository autorRepository;

    private final String RUTA_WORD = "uploads/word/";
    private final String RUTA_IMG = "uploads/img/";

    @Override
    public Articulo registrarArticulo(
            Articulo articulo,
            MultipartFile archivo,
            List<MultipartFile> imagenes,
            Long autorPrincipalId,
            List<Long> autoresSecundariosIds
    ) throws AplicacionExcepcion {

        log.debug("> Entrando a registrarArticulo");

        try {

            // ========================
            // 📅 FECHA AUTOMÁTICA
            // ========================
            // (ya viene desde controller, pero por seguridad)
            if (articulo.getFechaRegistro() == null) {
                articulo.setFechaRegistro(java.time.LocalDate.now());
            }

            // ========================
            // 📄 GUARDAR WORD
            // ========================
            if (archivo != null && !archivo.isEmpty()) {

                String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();

                File carpeta = new File(RUTA_WORD);
                if (!carpeta.exists()) carpeta.mkdirs();

                Files.write(Paths.get(RUTA_WORD + nombreArchivo), archivo.getBytes());

                articulo.setArchivoWord(nombreArchivo);
            }

            // ========================
            // 🖼️ GUARDAR IMÁGENES
            // ========================
            List<String> rutasImagenes = new ArrayList<>();

            if (imagenes != null) {

                if (imagenes.size() > 5) {
                    throw new AplicacionExcepcion("Máximo 5 imágenes permitidas");
                }

                File carpetaImg = new File(RUTA_IMG);
                if (!carpetaImg.exists()) carpetaImg.mkdirs();

                for (MultipartFile img : imagenes) {

                    if (!img.isEmpty()) {

                        String nombreImg = System.currentTimeMillis() + "_" + img.getOriginalFilename();

                        Files.write(Paths.get(RUTA_IMG + nombreImg), img.getBytes());

                        rutasImagenes.add(nombreImg);
                    }
                }
            }

            articulo.setImagenes(rutasImagenes);

            // ========================
            // 👤 AUTOR PRINCIPAL
            // ========================
            if (autorPrincipalId != null) {

                Autor autorPrincipal = autorRepository.findById(autorPrincipalId)
                        .orElseThrow(() -> new AplicacionExcepcion("Autor principal no encontrado"));

                articulo.setAutorPrincipal(autorPrincipal);
            }

            // ========================
            // 👥 AUTORES SECUNDARIOS
            // ========================
            if (autoresSecundariosIds != null && !autoresSecundariosIds.isEmpty()) {

                List<Autor> autores = (List<Autor>) autorRepository.findAllById(autoresSecundariosIds);

                articulo.setAutoresSecundarios(autores);
            }

            // ========================
            // 📊 VALIDACIÓN
            // ========================
            if (articulo.getTitulo() == null || articulo.getTitulo().isEmpty()) {
                throw new AplicacionExcepcion("El título es obligatorio");
            }

            // ========================
            // 💾 GUARDAR
            // ========================
            return articuloRepository.save(articulo);

        } catch (IOException e) {

            log.error(e.getMessage());
            throw new AplicacionExcepcion("Error al guardar archivos");

        } catch (DataAccessException e) {

            log.error(e.getMessage());
            throw new AplicacionExcepcion("Error en base de datos");
        }
    }

    @Override
    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    // ========================
    // ❌ ELIMINAR
    // ========================
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }

    // ========================
    // 🔍 OBTENER POR ID
    // ========================
    public Articulo obtenerPorId(Long id) {
        return articuloRepository.findById(id).orElse(null);
    }

}