package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.Errores.RecursoNoEncontradoException;
import es.safareyes.schoolevents.dto.request.CategoriaRequest;
import es.safareyes.schoolevents.dto.request.EventoRequest;
import es.safareyes.schoolevents.dto.response.CategoriaResponse;
import es.safareyes.schoolevents.dto.response.EventoResponse;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.mapper.CategoriaMapper;
import es.safareyes.schoolevents.modelos.Categoria;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Lugar;
import es.safareyes.schoolevents.repositorios.ICategoriaRepository;
import es.safareyes.schoolevents.servicios.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    // Mis dos ayudantes principales
    private ICategoriaRepository categoriaRepository;
    private CategoriaMapper categoriaMapper;

    // 1. OBTENER TODAS LAS CATEGORÍAS
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {

        // PASO A: Le pedimos a PostgreSQL todas las filas de la tabla categoria
        List<Categoria> listaEntidades = categoriaRepository.findAll();

        // PASO B: Preparamos la lista vacía donde guardaremos los DTOs de respuesta
        List<CategoriaResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Recorremos cada entidad una por una con un bucle for tradicional
        for (Categoria entidad : listaEntidades) {

            // Convertimos la entidad de la BD a un DTO de respuesta
            CategoriaResponse dto = categoriaMapper.aResponse(entidad);

            // Guardamos el DTO en nuestra lista final
            listaRespuestas.add(dto);
        }

        // PASO D: Devolvemos la lista rellena con los DTOs
        return listaRespuestas;
    }

    // 2. BUSCAR UNA CATEGORÍA POR SU ID
    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Integer id) {

        // PASO A: Buscamos en la base de datos (findById nos devuelve un Optional por si no existe)
        Optional<Categoria> resultadoBusqueda = categoriaRepository.findById(id);

        // PASO B: Comprobamos si el resultado está vacío
        if (resultadoBusqueda.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe ninguna categoría con el ID: " + id);
        }

        // PASO C: Si existe, extraemos la entidad real de dentro del Optional
        Categoria entidadEncontrada = resultadoBusqueda.get();

        //Devolvemos la respuesta convertida a DTO
        return categoriaMapper.aResponse(entidadEncontrada);
    }

    // 3. CREAR UNA NUEVA CATEGORÍA
    @Override
    @Transactional
    public CategoriaResponse crear(CategoriaRequest request) {

        // PASO A: Convertimos los datos que vienen de la petición (DTO Request) a una Entidad Categoria
        Categoria nuevaEntidad = categoriaMapper.aEntidad(request);

        // PASO B: Guardamos la nueva entidad en la base de datos PostgreSQL
        Categoria entidadGuardada = categoriaRepository.save(nuevaEntidad);

        // PASO C: Convertimos la entidad recién guardada en un DTO de respuesta
        // PASO D: Devolvemos el DTO
        return categoriaMapper.aResponse(entidadGuardada);
    }

    //eliminar categoria
    public void eliminar(Integer id) {

        boolean existe = categoriaRepository.existsById(id);

        if (!existe) {
            throw new RecursoNoEncontradoException("No se puede eliminar. No existe el evento con ID: " + id);
        }

        categoriaRepository.deleteById(id);
    }

    //actualizar categoria
    @Override
    @Transactional
    public CategoriaResponse actualizar(Integer id, CategoriaRequest request) {

        Categoria categoriaExiste = categoriaRepository.findById(id).orElse(null);

        if (categoriaExiste == null) {
            throw new RecursoNoEncontradoException("No se puede actualizar. No existe el evento con ID: " + id);
        }

        // Se mapean los nuevos valores sobre la entidad recuperada
        categoriaExiste.setNombre(request.nombre());
        categoriaExiste.setColor(request.color());
        categoriaExiste.setIcono(request.icono());

        Categoria actualizado = categoriaRepository.save(categoriaExiste);

        CategoriaResponse dto = categoriaMapper.aResponse(actualizado);
        return dto;
    }
}