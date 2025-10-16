package flor_de_lotus.usuario;


import flor_de_lotus.config.GerenciadorTokenJwt;
import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.endereco.dto.EnderecoMapper;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.exception.UnauthorizedException;
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
import org.springframework.web.server.ResponseStatusException;

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
        Endereco endereco = new Endereco();
        EnderecoResponse enderecoConsultado = enderecoService.buscarCEP(cep);
        endereco = EnderecoMapper.toEntityPost(enderecoConsultado, numero, complemento);

        return enderecoRepository.save(endereco);
    }

    public UsuarioResponseBody cadastrar(UsuarioPostRequestBody dto){

        checarDuplicidade(dto.getEmail(), dto.getCpf());
        checarRegrasSenha(dto.getSenha());

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        dto.setSenha(senhaCriptografada);

        Endereco endereco = cadastrarEndereco(dto.getCep(), dto.getNumero(), dto.getComplemento());

        Usuario usuario = UsuarioMapper.of(dto, endereco);

        UsuarioResponseBody usuarioResponseBody = UsuarioMapper.of(repository.save(usuario));

        return usuarioResponseBody;

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

        return UsuarioMapper.of(usuarioAutenticado,token);

    }

    public Usuario buscarPorIdOuThrow(Integer id){
        Optional<Usuario> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Usuário não encontrado");
        }

        return userOpt.get();

    }

    public void deletePorId(Integer id){
        repository.delete(buscarPorIdOuThrow(id));
    }

    public Usuario atulizarParcial(Integer id, UsuarioReplaceRequestBody body){
        Usuario usuario = buscarPorIdOuThrow(id);
        if (body.getEmail() != null) checarDuplicidade(body.getEmail(), null);usuario.setEmail(body.getEmail());
        if (body.getNome() != null) usuario.setNome(body.getNome());
        if (body.getSenha() != null) checarRegrasSenha(body.getSenha()); usuario.setSenha(body.getSenha());
        if (body.getTelefone() != null) usuario.setTelefone(body.getTelefone());

        return repository.save(usuario);

    }

    public List<UsuarioResponseBody> listarTodos(){
        List<Usuario> usuariosEncontrados = repository.findAll();
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }

}
