package br.edu.nassau.api_cursos.controller;

import br.edu.nassau.api_cursos.model.Curso;
import br.edu.nassau.api_cursos.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);
        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return curso;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso cadastrar(@RequestBody Curso curso) {
        return cursoService.cadastrar(curso);
    }

    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso atualizado = cursoService.atualizar(id, curso);
        if (atualizado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return atualizado;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        boolean removido = cursoService.remover(id);
        if (!removido) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
