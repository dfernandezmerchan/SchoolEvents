package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.LugarRequest;
import es.safareyes.schoolevents.dto.response.LugarResponse;
import es.safareyes.schoolevents.mapper.LugarMapper;
import es.safareyes.schoolevents.modelos.Lugar;
import es.safareyes.schoolevents.repositorios.ILugarRepository;
import es.safareyes.schoolevents.servicios.LugarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LugarServiceImpl implements LugarService {

    private final ILugarRepository lugarRepository;
    private final LugarMapper lugarMapper;

    public LugarServiceImpl(ILugarRepository lugarRepository, LugarMapper lugarMapper) {
        this.lugarRepository = lugarRepository;
        this.lugarMapper = lugarMapper;
    }

    // 1. LISTAR TODOS
    @Override
    @Transactional(readOnly = true)
    public List<LugarResponse> listarTodos() {

        List<Lugar> listaEntidades = lugarRepository.findAll();
        List<LugarResponse> listaRespuestas = new ArrayList<>();

        for (Lugar lugar : listaEntidades) {
            LugarResponse dto = lugarMapper.aResponse(lugar);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. BUSCAR POR ID
    @Override
    @Transactional(readOnly = true)
    public LugarResponse buscarPorId(Integer id) {

        Lugar lugar = lugarRepository.findById(id).orElse(null);

        if (lugar == null) {
            throw new RuntimeException("No existe ningún lugar con el ID: " + id);
        }

        LugarResponse dto = lugarMapper.aResponse(lugar);
        return dto;
    }

    // 3. CREAR LUGAR
    @Override
    @Transactional
    public LugarResponse crear(LugarRequest request) {

        Lugar nuevoLugar = lugarMapper.aEntidad(request);
        Lugar guardado = lugarRepository.save(nuevoLugar);

        LugarResponse dto = lugarMapper.aResponse(guardado);
        return dto;
    }

    // 4. ACTUALIZAR LUGAR
    @Override
    @Transactional
    public LugarResponse actualizar(Integer id, LugarRequest request) {

        Lugar lugarExistente = lugarRepository.findById(id).orElse(null);

        if (lugarExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe el lugar con ID: " + id);
        }

        // Se actualizan únicamente los campos de tu modelo Lugar
        lugarExistente.setNombre(request.nombre());
        lugarExistente.setAforomaximo(request.aforoMaximo());

        Lugar actualizado = lugarRepository.save(lugarExistente);

        LugarResponse dto = lugarMapper.aResponse(actualizado);
        return dto;
    }

    // 5. ELIMINAR LUGAR
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = lugarRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el lugar con ID: " + id);
        }

        lugarRepository.deleteById(id);
    }
}