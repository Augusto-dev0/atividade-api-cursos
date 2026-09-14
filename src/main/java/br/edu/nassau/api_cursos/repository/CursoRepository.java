package br.edu.nassau.api_cursos.repository;

import br.edu.nassau.api_cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}