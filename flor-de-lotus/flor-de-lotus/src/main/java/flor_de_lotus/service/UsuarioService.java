package flor_de_lotus.service;


import flor_de_lotus.entity.Usuario;
import flor_de_lotus.repository.UsuarioRepository;
import flor_de_lotus.request.UsuarioLoginRequestBody;
import flor_de_lotus.request.UsuarioPostRequestBody;
import flor_de_lotus.request.UsuarioReplaceRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    public final UsuarioRepository repository;


    public Usuario cadastrar(UsuarioPostRequestBody dto){
        checarDuplicidade(dto.getEmail(), dto.getCpf());
        checarRegrasSenha(dto.getSenha());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(dto.getSenha());
        usuario.setFkEndereco(dto.getFkEndereco());

        return repository.save(usuario);
    }

    public void checarDuplicidade(String email, String cpf){
        if (repository.existsByCpf(cpf)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Esse email já está em uso");
        }

        if (repository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.valueOf(409), "Tente novamente");
        }
    }

    public void checarRegrasSenha(String senha){
        //Aceitar apenas caracteres alfanuméricos: A-Z, a-z, 0- Ter entre 8 e 32 caracteres ao todo//
        // Exigir que a senha tenha ao menos: 1 letra maiúscula, 1 minúscula e 1 número.//
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,32}$");
        Matcher matcher = pattern.matcher(senha);
        if (!matcher.find()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Senha inválida, verifique os requisitos");
        }
    }

    public Usuario login(UsuarioLoginRequestBody dto){
        Optional<Usuario> userOpt = repository.findByEmail(dto.getEmail());
       if (userOpt.isEmpty()){
           throw new ResponseStatusException(HttpStatus.valueOf(404), "Usuário não encontrado");
       }

       Usuario save = userOpt.get();
       if (!save.getSenha().equals(dto.getSenha())){
           throw new ResponseStatusException(HttpStatus.valueOf(401), "Senha inválida");
       }

       return save;

    }

    public Usuario findByIdOrThrowBadRequest(Integer id){
        Optional<Usuario> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(404), "Usuário não encontrado");
        }

        return userOpt.get();

    }

    public void deletePorId(Integer id){
        repository.delete(findByIdOrThrowBadRequest(id));
    }

    public Usuario atulizarParcial(Integer id,UsuarioReplaceRequestBody body){
        Usuario usuario = findByIdOrThrowBadRequest(id);
        if (body.getEmail() != null) checarDuplicidade(body.getEmail(), null);usuario.setEmail(body.getEmail());
        if (body.getNome() != null) usuario.setNome(body.getNome());
        if (body.getSenha() != null) checarRegrasSenha(body.getSenha()); usuario.setSenha(body.getSenha());
        if (body.getTelefone() != null) usuario.setTelefone(body.getTelefone());

        return repository.save(usuario);

    }

    public Usuario buscarPorId(Integer id){
       return findByIdOrThrowBadRequest(id);
    }






}
