package br.insper.provaAI.curso.repository;

import br.insper.provaAI.curso.model.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends MongoRepository<Curso, String> {

    public List<Curso> findByNome(String nome);
}
