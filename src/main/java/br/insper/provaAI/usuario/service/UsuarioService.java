package br.insper.provaAI.usuario.service;

import br.insper.provaAI.usuario.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class UsuarioService {

        public ResponseEntity<Usuario> getUsuario(String cpf) {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForEntity(
                    "http://184.72.80.215:8080/usuario/" + cpf,
                    Usuario.class);
        }
}
