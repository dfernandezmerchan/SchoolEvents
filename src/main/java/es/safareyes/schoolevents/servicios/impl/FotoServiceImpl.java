package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.FotoRequest;
import es.safareyes.schoolevents.dto.response.FotoResponse;
import es.safareyes.schoolevents.mapper.FotoMapper;
import es.safareyes.schoolevents.modelos.Foto;
import es.safareyes.schoolevents.modelos.Publicacion;
import es.safareyes.schoolevents.repositorios.IFotoRepository;
import es.safareyes.schoolevents.repositorios.IPublicacionRepository;
import es.safareyes.schoolevents.servicios.FotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FotoServiceImpl implements FotoService {

    private final IFotoRepository fotoRepository;
    private final IPublicacionRepository publicacionRepository;
    private final FotoMapper fotoMapper;

    public FotoServiceImpl(IFotoRepository fotoRepository,
                           IPublicacionRepository publicacionRepository,
                           FotoMapper fotoMapper) {
        this.fotoRepository = fotoRepository;
        this.publicacionRepository = publicacionRepository;
        this.fotoMapper = fotoMapper;
    }

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
            throw new RuntimeException("No existe ninguna foto con el ID: " + id);
        }

        FotoResponse dto = fotoMapper.aResponse(foto);
        return dto;
    }

    // 4. CREAR FOTO
    @Override
    @Transactional
    public FotoResponse crear(FotoRequest request) {

        Foto nuevaFoto = fotoMapper.aEntidad(request);

        // Buscamos la publicación relacionada en PostgreSQL si viene su ID
        if (request.idPublicacion() != null) {
            Publicacion publicacionEncontrada = publicacionRepository.findById(request.idPublicacion()).orElse(null);

            if (publicacionEncontrada == null) {
                throw new RuntimeException("No existe la publicación con el ID: " + request.idPublicacion());
            }

            nuevaFoto.setPublicacion(publicacionEncontrada);
        }

        Foto guardada = fotoRepository.save(nuevaFoto);

        FotoResponse dto = fotoMapper.aResponse(guardada);
        return dto;
    }

    // 5. ACTUALIZAR FOTO
    @Override
    @Transactional
    public FotoResponse actualizar(Integer id, FotoRequest request) {

        Foto fotoExistente = fotoRepository.findById(id).orElse(null);

        if (fotoExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe la foto con ID: " + id);
        }

        // Actualizamos la URL
        fotoExistente.setUrl(request.url());

        // Actualizamos la relación con Publicacion
        if (request.idPublicacion() != null) {
            Publicacion publicacionEncontrada = publicacionRepository.findById(request.idPublicacion()).orElse(null);

            if (publicacionEncontrada == null) {
                throw new RuntimeException("No existe la publicación con el ID: " + request.idPublicacion());
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
            throw new RuntimeException("No se puede eliminar. No existe la foto con ID: " + id);
        }

        fotoRepository.deleteById(id);
    }
}