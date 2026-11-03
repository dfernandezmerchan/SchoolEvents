package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.NotificacionRequest;
import es.safareyes.schoolevents.dto.response.NotificacionResponse;
import es.safareyes.schoolevents.mapper.NotificacionMapper;
import es.safareyes.schoolevents.modelos.Evento;
import es.safareyes.schoolevents.modelos.Notificacion;
import es.safareyes.schoolevents.modelos.Usuario;
import es.safareyes.schoolevents.repositorios.IEventoRepository;
import es.safareyes.schoolevents.repositorios.INotificacionRepository;
import es.safareyes.schoolevents.repositorios.IUsuarioRepository;
import es.safareyes.schoolevents.servicios.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private INotificacionRepository notificacionRepository;
    private IUsuarioRepository usuarioRepository;
    private IEventoRepository eventoRepository;
    private NotificacionMapper notificacionMapper;

    // 1. LISTAR TODAS LAS NOTIFICACIONES
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarTodas() {

        List<Notificacion> listaEntidades = notificacionRepository.findAll();
        List<NotificacionResponse> listaRespuestas = new ArrayList<>();

        for (Notificacion notificacion : listaEntidades) {
            NotificacionResponse dto = notificacionMapper.aResponse(notificacion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. LISTAR NOTIFICACIONES POR USUARIO
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarPorUsuario(Integer usuarioId) {

        List<Notificacion> listaEntidades = notificacionRepository.findByUsuarioId(usuarioId);
        List<NotificacionResponse> listaRespuestas = new ArrayList<>();

        for (Notificacion notificacion : listaEntidades) {
            NotificacionResponse dto = notificacionMapper.aResponse(notificacion);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 3. BUSCAR NOTIFICACIÓN POR ID
    @Override
    @Transactional(readOnly = true)
    public NotificacionResponse buscarPorId(Integer id) {

        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);

        if (notificacion == null) {
            throw new RuntimeException("No existe ninguna notificación con el ID: " + id);
        }

        NotificacionResponse dto = notificacionMapper.aResponse(notificacion);
        return dto;
    }

    // 4. CREAR NOTIFICACIÓN
    @Override
    @Transactional
    public NotificacionResponse crear(NotificacionRequest request) {

        Notificacion nuevaNotificacion = notificacionMapper.aEntidad(request);

        if (request.idUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);
            if (usuario == null) {
                throw new RuntimeException("No existe el usuario con el ID: " + request.idUsuario());
            }
            nuevaNotificacion.setUsuario(usuario);
        }

        if (request.idEvento() != null) {
            Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);
            if (evento == null) {
                throw new RuntimeException("No existe el evento con el ID: " + request.idEvento());
            }
            nuevaNotificacion.setEvento(evento);
        }

        Notificacion guardada = notificacionRepository.save(nuevaNotificacion);

        NotificacionResponse dto = notificacionMapper.aResponse(guardada);
        return dto;
    }

    // 5. ACTUALIZAR NOTIFICACIÓN
    @Override
    @Transactional
    public NotificacionResponse actualizar(Integer id, NotificacionRequest request) {

        Notificacion notificacionExistente = notificacionRepository.findById(id).orElse(null);

        if (notificacionExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe la notificación con ID: " + id);
        }

        notificacionExistente.setMensaje(request.mensaje());
        notificacionExistente.setTipo(request.tipo());

        if (request.idUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(request.idUsuario()).orElse(null);
            if (usuario == null) {
                throw new RuntimeException("No existe el usuario con el ID: " + request.idUsuario());
            }
            notificacionExistente.setUsuario(usuario);
        } else {
            notificacionExistente.setUsuario(null);
        }

        if (request.idEvento() != null) {
            Evento evento = eventoRepository.findById(request.idEvento()).orElse(null);
            if (evento == null) {
                throw new RuntimeException("No existe el evento con el ID: " + request.idEvento());
            }
            notificacionExistente.setEvento(evento);
        } else {
            notificacionExistente.setEvento(null);
        }

        Notificacion actualizada = notificacionRepository.save(notificacionExistente);

        NotificacionResponse dto = notificacionMapper.aResponse(actualizada);
        return dto;
    }

    // 6. ELIMINAR NOTIFICACIÓN
    @Override
    @Transactional
    public void eliminar(Integer id) {

        boolean existe = notificacionRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe la notificación con ID: " + id);
        }

        notificacionRepository.deleteById(id);
    }

    // 7. MARCAR NOTIFICACIÓN COMO LEÍDA
    @Override
    @Transactional
    public void marcarComoLeida(Integer id) {

        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);

        if (notificacion == null) {
            throw new RuntimeException("No se puede marcar como leída. No existe la notificación con ID: " + id);
        }

        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }
}