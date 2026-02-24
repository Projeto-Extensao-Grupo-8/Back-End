package flor_de_lotus.usuario;


import flor_de_lotus.config.GerenciadorTokenJwt;
import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.endereco.dto.EnderecoMapper;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.endereco.EnderecoRepository;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import flor_de_lotus.usuario.dto.*;
import flor_de_lotus.endereco.EnderecoService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    public final UsuarioRepository repository;
    private final EnderecoService enderecoService;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Endereco cadastrarEndereco(String cep, String numero, String complemento){
        EnderecoResponse enderecoConsultado = enderecoService.buscarCEP(cep);
        Endereco endereco = EnderecoMapper.toEntityPost(enderecoConsultado, numero, complemento);

        return enderecoRepository.save(endereco);
    }

    public Usuario cadastrar(Usuario entity, String cep, String numero, String complemento){

        checarDuplicidade(entity.getEmail(), entity.getCpf());
        checarRegrasSenha(entity.getSenha());

        String senhaCriptografada = passwordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaCriptografada);

        Endereco endereco = cadastrarEndereco(cep, numero, complemento);

        entity.setFkEndereco(endereco);

        return repository.save(entity);

    }

    public void checarDuplicidade(String email, String cpf){
        if (repository.existsByCpf(cpf)){
            throw new EntidadeConflitoException("Esse cpf já está em uso");
        }

        if (repository.existsByEmail(email)){
            throw new EntidadeConflitoException("Esse email já está em uso");
        }
    }

    public void checarRegrasSenha(String senha){
        //Aceitar apenas caracteres alfanuméricos: A-Z, a-z, 0- Ter entre 8 e 32 caracteres ao todo//
        // Exigir que a senha tenha ao menos: 1 letra maiúscula, 1 minúscula e 1 número.//
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,32}$");
        Matcher matcher = pattern.matcher(senha);
        if (!matcher.find()){
            throw new BadRequestException("Verifique os requisitos");
        }
    }

    public UsuarioTokenResponseBody login(UsuarioLoginRequestBody dto){

        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = repository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new EntidadeNaoEncontradoException("Usuário não encontrado")
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.toTokenResponse(usuarioAutenticado,token);

    }

    public Usuario buscarPorIdOuThrow(Integer id){
        Optional<Usuario> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Usuário não encontrado");
        }

        return userOpt.get();

    }

    public Usuario buscarEntidadePorIdOuThrow(Integer id){
        Optional<Usuario> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Usuário não encontrado");
        }

        return userOpt.get();

    }

    public void deletePorId(Integer id){
        repository.delete(buscarEntidadePorIdOuThrow(id));
    }

    public Usuario atualizarParcial(Integer id, Usuario entity){
        Usuario usuario = buscarEntidadePorIdOuThrow(id);
        if (entity.getEmail() != null) checarDuplicidade(entity.getEmail(), null);usuario.setEmail(entity.getEmail());
        if (entity.getNome() != null) usuario.setNome(entity.getNome());
        if (entity.getSenha() != null) {
            checarRegrasSenha(entity.getSenha());
            usuario.setSenha(passwordEncoder.encode(entity.getSenha()));
        }
        if (entity.getTelefone() != null) usuario.setTelefone(entity.getTelefone());

        return repository.save(usuario);

    }

    public List<Usuario> listarTodos(){
        return repository.findAll();
    }

}
