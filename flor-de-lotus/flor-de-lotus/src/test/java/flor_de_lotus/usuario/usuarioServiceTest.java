package flor_de_lotus.usuario;

import flor_de_lotus.config.GerenciadorTokenJwt;
import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.endereco.EnderecoRepository;
import flor_de_lotus.endereco.EnderecoService;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.usuario.dto.UsuarioLoginRequestBody;
import flor_de_lotus.usuario.dto.UsuarioTokenResponseBody;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve cadastrar endereço com sucesso")
    void deveCadastrarEndereco() {

        EnderecoResponse response = new EnderecoResponse(
                "12345-999",
                "Rua das Flores",
                "Centro",
                "São Paulo",
                "SP"
        );

        Endereco endereco = new Endereco();

        when(enderecoService.buscarCEP("12345-999")).thenReturn(response);
        when(enderecoRepository.save(any())).thenReturn(endereco);

        Endereco result = service.cadastrarEndereco("12345-999", "100", "Casa");

        assertNotNull(result);
        verify(enderecoRepository).save(any());
    }

//    @Test
//    @DisplayName("Deve cadastrar usuário com sucesso")
//    void deveCadastrarUsuarioComSucesso() {
//        Usuario u = new Usuario();
//        u.setEmail("test@email.com");
//        u.setCpf("123");
//        u.setSenha("Senha123");
//
//        Endereco endereco = new Endereco();
//
//        when(repository.existsByCpf("123")).thenReturn(false);
//        when(repository.existsByEmail("test@email.com")).thenReturn(false);
//
//        when(passwordEncoder.encode("Senha123")).thenReturn("HASH");
//
//        when(enderecoService.buscarCEP("12345-999")).thenReturn(
//                new EnderecoResponse(
//                        "12345-999",
//                        "Rua das Flores",
//                        "Centro",
//                        "São Paulo",
//                        "SP"
//                )
//        );
//
//        when(enderecoRepository.save(any())).thenReturn(endereco);
//        when(repository.save(u)).thenReturn(u);
//
//        Usuario result = service.cadastrar(u, "12345-999", "10", "Casa");
//
//        assertEquals("HASH", result.getSenha());
//        assertEquals(endereco, result.getFkEndereco());
//        verify(repository).save(u);
//    }

    @Test
    @DisplayName("Deve lançar exceção de duplicidade ao cadastrar")
    void deveLancarDuplicidadeAoCadastrar() {
        Usuario u = new Usuario();
        u.setEmail("mail@mail.com");
        u.setCpf("123");

        when(repository.existsByCpf("123")).thenReturn(true);

        assertThrows(EntidadeConflitoException.class,
                () -> service.cadastrar(u, "12345-999", "10", "Casa"));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve aceitar senha válida")
    void deveAceitarSenhaValida() {
        assertDoesNotThrow(() -> service.checarRegrasSenha("Senha123"));
    }

    @Test
    @DisplayName("Deve rejeitar senha inválida")
    void deveRejeitarSenhaInvalida() {
        assertThrows(BadRequestException.class,
                () -> service.checarRegrasSenha("123"));
    }

//    @Test
//    @DisplayName("Deve realizar login com sucesso gerando token")
//    void deveFazerLogin() {
//
//        UsuarioLoginRequestBody dto = new UsuarioLoginRequestBody();
//        dto.setEmail("user@mail.com");
//        dto.setSenha("123");
//
//        Authentication authMock = mock(Authentication.class);
//
//        when(authenticationManager.authenticate(any())).thenReturn(authMock);
//
//        Usuario usuarioBD = new Usuario();
//        usuarioBD.setEmail("user@mail.com");
//
//        when(repository.findByEmail("user@mail.com")).thenReturn(Optional.of(usuarioBD));
//        when(gerenciadorTokenJwt.generateToken(authMock)).thenReturn("TOKEN-TESTE");
//
//        UsuarioTokenResponseBody result = service.login(dto);
//
//        assertEquals("TOKEN-TESTE", result.getToken());
//    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarPorId() {
        Usuario u = new Usuario();
        u.setIdUsuario(10);

        when(repository.findById(10)).thenReturn(Optional.of(u));

        Usuario result = service.buscarPorIdOuThrow(10);

        assertEquals(10, result.getIdUsuario());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscar() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.buscarPorIdOuThrow(999));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuario() {
        Usuario u = new Usuario();

        when(repository.findById(5)).thenReturn(Optional.of(u));

        service.deletePorId(5);

        verify(repository).delete(u);
    }

    @Test
    @DisplayName("Deve atualizar parcialmente o usuário")
    void deveAtualizarParcial() {

        Usuario existente = new Usuario();
        existente.setIdUsuario(1);

        Usuario entrada = new Usuario();
        entrada.setEmail("novo@mail.com");

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.existsByEmail("novo@mail.com")).thenReturn(false);
        when(repository.save(existente)).thenReturn(existente);

        Usuario result = service.atulizarParcial(1, entrada);

        assertEquals("novo@mail.com", result.getEmail());
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void deveListarTodos() {
        when(repository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        List<Usuario> lista = service.listarTodos();

        assertEquals(2, lista.size());
    }
}
