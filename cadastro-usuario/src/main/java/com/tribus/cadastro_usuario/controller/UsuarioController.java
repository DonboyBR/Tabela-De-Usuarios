package com.tribus.cadastro_usuario.controller;

import com.tribus.cadastro_usuario.business.UsuarioService;
import com.tribus.cadastro_usuario.infrastructure.entitys.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Essa anotação diz pro Spring que essa classe é um controlador REST.
// Ou seja, ela vai receber requisições HTTP e devolver respostas, geralmente em JSON.
@RestController
// Define o caminho base pra todos os endpoints aqui.
// Tipo, todas as URLs que esse controller gerencia vão começar com "/usuario".
@RequestMapping("/usuario")
// Essa anotação do Lombok já cria um construtor com todos os campos 'final'
@RequiredArgsConstructor
public class UsuarioController {

    // Aqui declara o 'UsuarioService'. Ele é o cara que vai fazer a lógica de negócio.
    // O Lombok vai injetar ele pra gente por causa do @RequiredArgsConstructor.
    private final UsuarioService usuarioService;

    // Endpoint pra salvar um novo usuário.
    // Ele responde a requisições POST para "/usuario".
    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usuario){
        // Manda o usuário que veio na requisição pro serviço salvar no banco.
        usuarioService.salvarUsuario(usuario);
        // Se deu tudo certo, devolve uma resposta HTTP 200 (OK), sem corpo.
        return ResponseEntity.ok().build();
    }

    // Endpoint pra buscar um usuário por email.
    // Ele responde a requisições GET para "/usuario", esperando um parâmetro 'email'.
    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email){
        // Chama o serviço pra buscar o usuário e devolve o resultado com um HTTP 200 (OK).
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    // Endpoint pra deletar um usuário por email.
    // Ele responde a requisições DELETE para "/usuario", esperando um parâmetro 'email'.
    @DeleteMapping
    public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam String email){
        // Manda o email pro serviço deletar o usuário.
        usuarioService.deletarUsuarioPorEmail(email);
        // Se deu tudo certo, devolve um HTTP 200 (OK), sem corpo.
        return ResponseEntity.ok().build();
    }

    // Endpoint pra atualizar um usuário pelo ID.
    // Ele responde a requisições PUT para "/usuario", esperando um 'id' na URL
    // e os dados atualizados do usuário no corpo da requisição.
    @PutMapping
    public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Integer id,
                                                      @RequestBody Usuario usuario){
        // Manda o ID e os novos dados do usuário pro serviço atualizar.
        usuarioService.atualizarUsuarioPorId(id, usuario);
        // Se deu tudo certo, devolve um HTTP 200 (OK), sem corpo.
        return ResponseEntity.ok().build();
    }
}