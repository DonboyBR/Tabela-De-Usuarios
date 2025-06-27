package com.tribus.cadastro_usuario.business;

import com.tribus.cadastro_usuario.infrastructure.entitys.Usuario;
import com.tribus.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

// Essa anotação diz pro Spring que essa classe é um "serviço".
// É tipo o cérebro das operações, onde a gente coloca as regras de negócio.
@Service
public class UsuarioService {

    // Aqui declara o repositório. Ele é quem faz a ponte com o banco de dados.
    private final UsuarioRepository repository;

    // Construtor do nosso serviço. O Spring injeta o 'UsuarioRepository' aqui.
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository; // Atribui o repositório injetado.
    }

    // Método pra salvar um usuário novo ou atualizar um que já existe.
    public void salvarUsuario(Usuario usuario){
        repository.saveAndFlush(usuario); // 'saveAndFlush' salva e garante que a mudança vá pro banco na hora.
    }

    // Método pra buscar um usuário pelo email.
    public Usuario buscarUsuarioPorEmail(String email){
        // Se não encontrar, ele manda uma mensagem de "Email não encontrado".
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado")
        );
    }

    // Método pra deletar um usuário pelo email.
    public void deletarUsuarioPorEmail(String email){
        repository.deleteByEmail(email);
    }

    // Método pra atualizar os dados de um usuário pelo ID.
    public void atualizarUsuarioPorId(Integer id, Usuario usuario){
        // Primeiro busca o usuário que queremos atualizar pelo ID.
        // Se não achar, lança um erro.
        Usuario usuarioEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuario não encontrado"));

        // Cria um novo objeto Usuario com os dados atualizados.
        // Se mandar um nome/email novo, ele usa. Se não, ele mantém o antigo.
        // O ID tem que ser o do usuário existente pra ele saber quem atualizar.
        Usuario usuarioAtualizado = Usuario.builder()
                .email(usuario.getEmail() != null ? usuario.getEmail() : // Se o email foi passado, usa o novo, senão o velho.
                        usuarioEntity.getEmail())
                .nome(usuario.getNome() != null ? usuario.getNome() :     // Se o nome foi passado, usa o novo, senão o velho.
                        usuarioEntity.getNome())
                .id(usuarioEntity.getId()) // Mantém o ID original pra atualizar o certo.
                .build();

        // Salva as atualizações no banco de dados.
        repository.saveAndFlush(usuarioAtualizado);
    }
}