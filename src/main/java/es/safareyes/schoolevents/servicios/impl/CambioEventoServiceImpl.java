package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.response.CambioEventoResponse;
import es.safareyes.schoolevents.mapper.CambioEventoMapper;
import es.safareyes.schoolevents.modelos.CambioEvento;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.repositorios.ICambioEventoRepository;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.servicios.CambioEventoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CambioEventoServiceImpl implements CambioEventoService {

    private ICambioEventoRepository cambioEventoRepository;
    private IEventoRepository eventoRepository;
    private CambioEventoMapper cambioEventoMapper;

    // 1. LISTAR TODOS LOS CAMBIOS
    @Override
    @Transactional(readOnly = true)
    public List<CambioEventoResponse> listarTodos() {

        List<CambioEvento> listaEntidades = cambioEventoRepository.findAll();
        List<CambioEventoResponse> listaRespuestas = new ArrayList<>();

        for (CambioEvento cambio : listaEntidades) {
            CambioEventoResponse dto = cambioEventoMapper.aResponse(cambio);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. LISTAR HISTORIAL DE CAMBIOS DE UN EVENTO
    @Override
    @Transactional(readOnly = true)
    public List<CambioEventoResponse> listarPorEvento(Integer eventoId) {

        List<CambioEvento> listaEntidades = cambioEventoRepository.findByEventoId(eventoId);
        List<CambioEventoResponse> listaRespuestas = new ArrayList<>();

        for (CambioEvento cambio : listaEntidades) {
            CambioEventoResponse dto = cambioEventoMapper.aResponse(cambio);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. BUSCAR UN REGISTRO DE CAMBIO POR ID
    @Override
    @Transactional(readOnly = true)
    public CambioEventoResponse buscarPorId(Integer id) {

        CambioEvento cambio = cambioEventoRepository.findById(id).orElse(null);

        if (cambio == null) {
            throw new RuntimeException("No existe ningún registro de cambio con el ID: " + id);
        }

        CambioEventoResponse dto = cambioEventoMapper.aResponse(cambio);
        return dto;
    }

    // 4. REGISTRAR UN CAMBIO DE EVENTO (USADO INTERNAMENTE O DESDE CONTROLADOR)
    @Override
    @Transactional
    public CambioEventoResponse registrarCambio(Integer idEvento, String campoModificado, String valorAnterior, String valorNuevo) {

        // Buscar el evento relacionado
        Evento evento = eventoRepository.findById(idEvento).orElse(null);

        if (evento == null) {
            throw new RuntimeException("No existe el evento con el ID: " + idEvento);
        }

        // Instanciar la entidad directamente
        CambioEvento cambio = new CambioEvento();
        cambio.setCampoModificado(campoModificado);
        cambio.setValorAnterior(valorAnterior);
        cambio.setValorNuevo(valorNuevo);
        cambio.setFecha(LocalDateTime.now());
        cambio.setEvento(evento);

        CambioEvento guardado = cambioEventoRepository.save(cambio);

        CambioEventoResponse dto = cambioEventoMapper.aResponse(guardado);
        return dto;
    }

    // 5. ELIMINAR REGISTRO DE CAMBIO POR ID
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = cambioEventoRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el registro de cambio con ID: " + id);
        }

        cambioEventoRepository.deleteById(id);
    }
}