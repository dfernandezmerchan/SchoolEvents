package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.Errores.RecursoNoEncontradoException;
import es.safareyes.schoolevents.dto.request.FotoRequest;
import es.safareyes.schoolevents.dto.response.FotoResponse;
import es.safareyes.schoolevents.mapper.FotoMapper;
import es.safareyes.schoolevents.modelos.Foto;
import es.safareyes.schoolevents.modelos.Publicacion;
import es.safareyes.schoolevents.repositorios.IFotoRepository;
import es.safareyes.schoolevents.repositorios.IPublicacionRepository;
import es.safareyes.schoolevents.servicios.FotoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FotoServiceImpl implements FotoService {

    private IFotoRepository fotoRepository;
    private IPublicacionRepository publicacionRepository;
    private FotoMapper fotoMapper;

    // 1. LISTAR TODAS LAS FOTOS
    @Override
    @Transactional(readOnly = true)
    public List<FotoResponse> listarTodas() {

        List<Foto> listaEntidades = fotoRepository.findAll();
        List<FotoResponse> listaRespuestas = new ArrayList<>();

        for (Foto foto : listaEntidades) {
            FotoResponse dto = fotoMapper.aResponse(foto);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. LISTAR FOTOS DE UNA PUBLICACIÓN
    @Override
    @Transactional(readOnly = true)
    public List<FotoResponse> listarPorPublicacion(Integer publicacionId) {

        if(!publicacionRepository.existsById(publicacionId)) {
            throw new RecursoNoEncontradoException("no se ha encontrado ninguna publicación con el id" + publicacionId);
        }

        List<Foto> listaEntidades = fotoRepository.findByPublicacionId(publicacionId);
        List<FotoResponse> listaRespuestas = new ArrayList<>();

        for (Foto foto : listaEntidades) {
            FotoResponse dto = fotoMapper.aResponse(foto);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. BUSCAR POR ID
    @Override
    @Transactional(readOnly = true)
    public FotoResponse buscarPorId(Integer id) {

        Foto foto = fotoRepository.findById(id).orElse(null);

        if (foto == null) {
            throw new RecursoNoEncontradoException("No existe ninguna foto con el ID: " + id);
        }

        FotoResponse dto = fotoMapper.aResponse(foto);
        return dto;
    }

    // 4. CREAR FOTO
    @Override
    @Transactional
    public FotoResponse crearFoto(Integer publicacionId, FotoRequest request) {

        // 1. Buscamos la publicación por el ID que viene en la URL
        Publicacion publicacionEncontrada = publicacionRepository.findById(publicacionId).orElse(null);

        if(publicacionEncontrada == null) {
            throw new RecursoNoEncontradoException("No se puede actualizar. No existe el lugar con ID: " + publicacionId);
        }

        // 2. Mapeamos el DTO a la entidad Foto
        Foto nuevaFoto = fotoMapper.aEntidad(request);

        // 3. Asignamos la publicación encontrada (relación obligatoria)
        nuevaFoto.setPublicacion(publicacionEncontrada);

        // 4. Guardamos en la base de datos
        Foto guardada = fotoRepository.save(nuevaFoto);

        // 5. Retornamos el DTO de respuesta
        return fotoMapper.aResponse(guardada);
    }

    // 5. ACTUALIZAR FOTO
    @Override
    @Transactional
    public FotoResponse actualizar(Integer id, FotoRequest request) {

        Foto fotoExistente = fotoRepository.findById(id).orElse(null);

        if (fotoExistente == null) {
            throw new RecursoNoEncontradoException("No se puede actualizar. No existe la foto con ID: " + id);
        }

        // Actualizamos la URL
        fotoExistente.setUrl(request.url());

        // Actualizamos la relación con Publicacion
        if (request.idPublicacion() != null) {
            Publicacion publicacionEncontrada = publicacionRepository.findById(request.idPublicacion()).orElse(null);

            if (publicacionEncontrada == null) {
                throw new RecursoNoEncontradoException("No existe la publicación con el ID: " + request.idPublicacion());
            }

            fotoExistente.setPublicacion(publicacionEncontrada);
        } else {
            fotoExistente.setPublicacion(null);
        }

        Foto actualizada = fotoRepository.save(fotoExistente);

        FotoResponse dto = fotoMapper.aResponse(actualizada);
        return dto;
    }

    // 6. ELIMINAR FOTO
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = fotoRepository.existsById(id);

        if (!existe) {
            throw new RecursoNoEncontradoException("No se puede eliminar. No existe la foto con ID: " + id);
        }

        fotoRepository.deleteById(id);
    }
}