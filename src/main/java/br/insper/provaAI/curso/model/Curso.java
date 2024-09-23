package br.insper.provaAI.curso.model;

import br.insper.provaAI.usuario.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Getter
@Setter
public class Curso {
//    A rota deve receber o nome, a descrição e o número máximo de alunos.
//    Também deve ser passado o cpf do professor do curso. Deve ser verificado
//    se o professor existe na rota http://184.72.80.215:8080/usuario/{cpf}.
    @Id
    private String id;

    private String descricao;

    private Integer maxAlunos;

    private String cpfProfessor;

    private String nome;

    private ArrayList<Usuario> alunos;
}
