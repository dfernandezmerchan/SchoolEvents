package es.safareyes.schoolevents.servicios.impl;

import es.safareyes.schoolevents.dto.request.CursoRequest;
import es.safareyes.schoolevents.dto.response.CursoResponse;
import es.safareyes.schoolevents.mapper.CursoMapper;
import es.safareyes.schoolevents.modelos.Curso;
import es.safareyes.schoolevents.repositorios.ICursoRepository;
import es.safareyes.schoolevents.servicios.CursoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CursoServiceImpl implements CursoService {

    private ICursoRepository cursoRepository;
    private CursoMapper cursoMapper;

    // 1. LISTAR TODOS LOS CURSOS
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse>  listarTodos() {

        // PASO A: Traer todos los cursos de PostgreSQL
        List<Curso> listaEntidades = cursoRepository.findAll();

        // PASO B: Preparar lista vacía para los DTOs de respuesta
        List<CursoResponse> listaRespuestas = new ArrayList<>();

        // PASO C: Bucle for tradicional para mapear cada curso
        for (Curso curso : listaEntidades) {
            CursoResponse dto = cursoMapper.aResponse(curso);
            listaRespuestas.add(dto);
        }

        return listaRespuestas;
    }

    // 2. BUSCAR UN CURSO POR ID
    @Override
    @Transactional(readOnly = true)
    public CursoResponse buscarPorId(Integer id) {

        // PASO A: Buscar el curso con .orElse(null)
        Curso curso = cursoRepository.findById(id).orElse(null);

        // PASO B: Validación con 'if' tradicional
        if (curso == null) {
            throw new RuntimeException("No existe ningún curso con el ID: " + id);
        }

        // PASO C: Convertir a DTO y devolver
        CursoResponse dto = cursoMapper.aResponse(curso);
        return dto;
    }

    // 3. CREAR UN NUEVO CURSO
    @Override
    @Transactional
    public CursoResponse crear(CursoRequest request) {

        // PASO A: Convertir el DTO Request a Entidad
        Curso nuevoCurso = cursoMapper.aEntidad(request);

        // PASO B: Guardar en PostgreSQL
        Curso guardado = cursoRepository.save(nuevoCurso);

        // PASO C: Convertir la entidad guardada a DTO de respuesta y devolver
        CursoResponse dto = cursoMapper.aResponse(guardado);
        return dto;
    }

    // 4. ACTUALIZAR UN CURSO EXISTENTE
    @Override
    @Transactional
    public CursoResponse actualizar(Integer id, CursoRequest request) {

        // PASO A: Buscar el curso a modificar
        Curso cursoExistente = cursoRepository.findById(id).orElse(null);

        if (cursoExistente == null) {
            throw new RuntimeException("No se puede actualizar. No existe el curso con ID: " + id);
        }

        // PASO B: Modificar los campos directos de la entidad Curso
        cursoExistente.setNombre(request.nombre());
        cursoExistente.setPromocion(request.promocion());

        // PASO C: Guardar las modificaciones en PostgreSQL
        Curso actualizado = cursoRepository.save(cursoExistente);

        // PASO D: Convertir entidad actualizada a DTO y devolver
        CursoResponse dto = cursoMapper.aResponse(actualizado);
        return dto;
    }

    // 5. ELIMINAR UN CURSO POR ID
    @Override
    @Transactional
    public void eliminar(Integer id) {

        // PASO A: Comprobar si existe la fila en la BD
        boolean existe = cursoRepository.existsById(id);

        if (!existe) {
            throw new RuntimeException("No se puede eliminar. No existe el curso con ID: " + id);
        }

        // PASO B: Eliminar de la base de datos
        cursoRepository.deleteById(id);
    }
}