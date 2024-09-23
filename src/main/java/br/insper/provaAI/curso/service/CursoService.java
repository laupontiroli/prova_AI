package br.insper.provaAI.curso.service;

import br.insper.provaAI.curso.model.Curso;
import br.insper.provaAI.curso.repository.CursoRepository;
import br.insper.provaAI.usuario.model.Usuario;
import br.insper.provaAI.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioService professorService;

    public Curso cadastrarCurso(Curso curso) {
        curso.setId(UUID.randomUUID().toString());
        ResponseEntity<Usuario> responseProfessor = professorService.getUsuario(curso.getCpfProfessor());

        if (responseProfessor.getStatusCode().is2xxSuccessful()) {
            return cursoRepository.save(curso);
        }
        throw new RuntimeException("Professor não cadastrado");
    }

    public List<Curso> getCurso(String nome){
        if (nome != null){
            return cursoRepository.findByNome(nome);
        }
        return cursoRepository.findAll();
    }

    public Curso addAluno(String idCurso, String cpf){
        ResponseEntity<Usuario> responseAluno = professorService.getUsuario(cpf);

        if (responseAluno.getStatusCode().is2xxSuccessful()) {
            Optional<Curso> op = cursoRepository.findById(idCurso);
            if (op.isPresent()){
                Curso curso = op.get();
                ArrayList<Usuario> alunos = curso.getAlunos();
                if(alunos.size() < curso.getMaxAlunos()){
                    curso.getAlunos().add(responseAluno.getBody());
                    return cursoRepository.save(curso);
                }
                throw new RuntimeException("Curso lotado");
            }
            throw new RuntimeException("Curso não encontrado");
        }
        throw new RuntimeException("Aluno não encontrado");
    }
}
