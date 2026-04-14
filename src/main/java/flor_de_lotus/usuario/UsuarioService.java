package flor_de_lotus.usuario;


import flor_de_lotus.config.GerenciadorTokenJwt;
import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.endereco.dto.EnderecoMapper;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.endereco.EnderecoRepository;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import flor_de_lotus.paciente.PacienteRepository;
import flor_de_lotus.funcionario.FuncionarioRepository;
import flor_de_lotus.usuario.dto.*;
import flor_de_lotus.endereco.EnderecoService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final PacienteRepository pacienteRepository;
    private final FuncionarioRepository funcionarioRepository;

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

    public Usuario cadastrar(Usuario entity){

        checarDuplicidade(entity.getEmail(), entity.getCpf());
        checarRegrasSenha(entity.getSenha());

        String senhaCriptografada = passwordEncoder.encode(entity.getSenha());
        entity.setSenha(senhaCriptografada);

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
        // Exigir que a senha tenha ao menos: 1 letra maiúscula, 1 minúscula e 1 número, caracter especial.//
        // Permite qualquer caractere na senha, desde que cumpra as 4 regras de obrigatoriedade
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,32}$");
        Matcher matcher = pattern.matcher(senha);
        if (!matcher.find()){
            throw new BadRequestException("Verifique os requisitos da senha");
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

        // Buscar IDs adicionais baseado no nível de permissão
        Integer idPaciente = null;
        Integer idFuncionario = null;

        // 1 = USUARIO/ADMIN, 2 = PACIENTE, 3 = FUNCIONARIO
        if ("2".equals(usuarioAutenticado.getNivelPermissao())) {
            // Buscar ID do Paciente
            var paciente = pacienteRepository.findByFkUsuario_IdUsuario(usuarioAutenticado.getIdUsuario());
            if (paciente.isPresent()) {
                idPaciente = paciente.get().getIdPaciente();
            }
        } else if ("3".equals(usuarioAutenticado.getNivelPermissao())) {
            // Buscar ID do Funcionário
            var funcionario = funcionarioRepository.findByFkUsuario_IdUsuario(usuarioAutenticado.getIdUsuario());
            if (funcionario.isPresent()) {
                idFuncionario = funcionario.get().getIdFuncionario();
            }
        }

        return UsuarioMapper.toTokenResponse(usuarioAutenticado, token, idPaciente, idFuncionario);

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

        if (entity.getEmail() != null && !entity.getEmail().equals(usuario.getEmail())) {
            if (repository.existsByEmail(entity.getEmail())) {
                throw new EntidadeConflitoException("Esse email já está em uso");
            }
            usuario.setEmail(entity.getEmail());
        }

        if (entity.getNome() != null) {
            usuario.setNome(entity.getNome());
        }

        if (entity.getSenha() != null) {
            checarRegrasSenha(entity.getSenha());
            usuario.setSenha(passwordEncoder.encode(entity.getSenha()));
        }

        if (entity.getTelefone() != null) {
            usuario.setTelefone(entity.getTelefone());
        }

        return repository.save(usuario);

    }

    public String gerarLinkWhatsapp(
            String data,
            String horario,
            String status,
            String email
    ) {

        System.out.println("Data: " + data);
        System.out.println("Horário: " + horario);
        System.out.println("Status: " + status);
        System.out.println("Email: " + email);

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado"));

        String mensagem;

        if (status.equalsIgnoreCase("Confirmada")) {
            mensagem = String.format(
                    "Olá, %s! \n" +
                            "\n" +
                            "Sua consulta foi *confirmada com sucesso*! ✨\n" +
                            "\n" +
                            "*Data:* %s\n" +
                            "*Horário:* %s\n" +
                            "\n" +
                            "Ficamos felizes em te acompanhar nesse momento.\n" +
                            "Qualquer dúvida ou necessidade de reagendamento, estamos por aqui. \n" +
                            "\n" +
                            "Com carinho,\n" +
                            "*Flor de Lótus* \n",
                    usuario.getNome(), data, horario
            );
        } else {
            mensagem = String.format(
                    "Olá, %s! \n" +
                            "\n" +
                            "Informamos que sua consulta foi *cancelada*.\n" +
                            "\n" +
                            "Sabemos que imprevistos acontecem, e estamos aqui para te ajudar.\n" +
                            "Se quiser reagendar, será um prazer te atender novamente. \n" +
                            "\n" +
                            "Com carinho,\n" +
                            "*Flor de Lótus* \n",
                    usuario.getNome()
            );
        }

        String mensagemEncoded = URLEncoder.encode(mensagem, StandardCharsets.UTF_8);

        return "https://wa.me/" + usuario.getTelefone() + "?text=" + mensagemEncoded;
    }

    public List<Usuario> listarTodos(){
        return repository.findAll();
    }

}
