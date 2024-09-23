package br.insper.provaAI.curso.controller;

import br.insper.provaAI.curso.model.Curso;
import br.insper.provaAI.curso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;
    @PostMapping
    public Curso cadastrarCurso(@RequestBody Curso curso){
        return cursoService.cadastrarCurso(curso);
    }

    @PostMapping("{id}/aluno/{cpf}")
    public Curso cadastrarCurso(@PathVariable String id,@PathVariable String cpf){
        return cursoService.addAluno(id,cpf);
    }

    @GetMapping
    public List<Curso> getCursos(@RequestParam(required = false) String nome){
        return cursoService.getCurso(nome);
    }
}
