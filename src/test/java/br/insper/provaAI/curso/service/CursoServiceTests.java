package br.insper.provaAI.curso.service;

import br.insper.provaAI.curso.model.Curso;
import br.insper.provaAI.curso.repository.CursoRepository;
import br.insper.provaAI.usuario.model.Usuario;
import br.insper.provaAI.usuario.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTests {
    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testListarCursosWhenNomeIsNull() {

        // preparacao
        Mockito.when(cursoRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada do codigo testado
        List<Curso> cursos = cursoService.getCurso(null);

        // verificacao dos resultados
        Assertions.assertTrue(cursos.isEmpty());
    }

    @Test
    public void testListarCursosWhenNomeIsNotNull() {
        Curso curso = new Curso();
        String nome = "NomeCurso";
        curso.setNome(nome);
        // preparacao

        // preparacao
        List<Curso> lista = new ArrayList<>();

        lista.add(curso);

        Mockito.when(cursoRepository.findByNome(Mockito.anyString())).thenReturn(lista);

        // chamada do codigo testado
        List<Curso> cursos = cursoService.getCurso("NomeCurso");

        // verificacao dos resultados
        Assertions.assertTrue(cursos.size() == 1);
        Assertions.assertEquals("NomeCurso", cursos.getFirst().getNome());
    }
    @Test
    public void addAlunoWhenAlunoNaoExiste() {
        // Arrange
            // Arrange
            Mockito.when(usuarioService.getUsuario("123")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            // Act & Assert
            Assertions.assertThrows(RuntimeException.class, () -> {
                cursoService.addAluno("1", "123");
            });
        }
    @Test
    public void addAlunoWhenCursoNaoExiste() {
        // Arrange
        Usuario aluno = new Usuario();
        aluno.setCpf("123");
        ResponseEntity<Usuario> alunoEntity = new ResponseEntity<>(aluno, HttpStatus.OK);

        Mockito.when(usuarioService.getUsuario("123")).thenReturn(alunoEntity);
        Mockito.when(cursoRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            cursoService.addAluno("1", "123");
        });
    }
    @Test
    public void addAlunoWhenCursoLotado() {
        // Arrange
        Curso curso = new Curso();
        curso.setId("1");
        curso.setMaxAlunos(1); // Capacidade máxima é 1
        ArrayList<Usuario> alunos = new ArrayList<>();
        alunos.add(new Usuario()); // Curso já tem 1 aluno
        curso.setAlunos(alunos);

        Usuario aluno = new Usuario();
        aluno.setCpf("123");
        ResponseEntity<Usuario> alunoEntity = new ResponseEntity<>(aluno, HttpStatus.OK);

        Mockito.when(usuarioService.getUsuario("123")).thenReturn(alunoEntity);
        Mockito.when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            cursoService.addAluno("1", "123");
        }, "Curso lotado");
    }
    @Test
    public void addAlunoWhenSuccess() {
        // Arrange
        Curso curso = new Curso();
        curso.setId("1");
        curso.setMaxAlunos(2); // Capacidade máxima é 2
        ArrayList<Usuario> alunos = new ArrayList<>();
        curso.setAlunos(alunos);

        Usuario aluno = new Usuario();
        aluno.setCpf("123");
        ResponseEntity<Usuario> alunoEntity = new ResponseEntity<>(aluno, HttpStatus.OK);

        Mockito.when(usuarioService.getUsuario("123")).thenReturn(alunoEntity);
        Mockito.when(cursoRepository.findById("1")).thenReturn(Optional.of(curso));
        Mockito.when(cursoRepository.save(Mockito.any(Curso.class))).thenReturn(curso);

        // Act
        Curso cursoRetorno = cursoService.addAluno("1", "123");

        // Assert
        Assertions.assertEquals(1, cursoRetorno.getAlunos().size());
        Assertions.assertEquals("123", cursoRetorno.getAlunos().get(0).getCpf());
    }

}
