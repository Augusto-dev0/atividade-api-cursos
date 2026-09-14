package br.edu.nassau.api_cursos.service;

import br.edu.nassau.api_cursos.model.Curso;
import br.edu.nassau.api_cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElse(null);
    }

    public Curso cadastrar(Curso curso) {
        validar(curso);
        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, Curso dados) {
        Curso curso = buscarPorId(id);
        if (curso == null) {
            return null;
        }
        validar(dados);
        curso.setNome(dados.getNome());
        curso.setCargaHoraria(dados.getCargaHoraria());
        return cursoRepository.save(curso);
    }

    public boolean remover(Long id) {
        if (!cursoRepository.existsById(id)) {
            return false;
        }
        cursoRepository.deleteById(id);
        return true;
    }

    private void validar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo nem ficar em branco");
        }
        if (curso.getCargaHoraria() == null || curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero");
        }
    }
}