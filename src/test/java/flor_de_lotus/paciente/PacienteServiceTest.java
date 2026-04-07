package flor_de_lotus.paciente;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private PacienteService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar paciente com sucesso")
    void deveCadastrarComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        Paciente p = new Paciente();

        when(usuarioService.buscarEntidadePorIdOuThrow(1)).thenReturn(usuario);
        when(repository.save(p)).thenReturn(p);

        Paciente result = service.cadastrar(p, 1);

        assertEquals(usuario, result.getFkUsuario());
        verify(repository).save(p);
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com usuário inexistente")
    void deveLancarExcecaoAoCadastrarUsuarioNaoEncontrado() {
        Paciente p = new Paciente();

        when(usuarioService.buscarEntidadePorIdOuThrow(5))
                .thenThrow(new EntidadeNaoEncontradoException("Usuário não encontrado"));

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.cadastrar(p, 5));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os pacientes")
    void deveListarTodos() {
        List<Paciente> lista = List.of(new Paciente(), new Paciente());

        when(repository.findAll()).thenReturn(lista);

        List<Paciente> result = service.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Deve buscar paciente por ID com sucesso")
    void deveBuscarPorId() {
        Paciente p = new Paciente();
        p.setIdPaciente(10);

        when(repository.findById(10)).thenReturn(Optional.of(p));

        Paciente result = service.buscarPorIdOuThrow(10);

        assertEquals(10, result.getIdPaciente());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar paciente inexistente")
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        when(repository.findById(30)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.buscarPorIdOuThrow(30));
    }

    @Test
    @DisplayName("Deve atualizar parcialmente trocando usuário")
    void deveAtualizarParcialComTrocaDeUsuario() {
        Paciente existente = new Paciente();
        existente.setIdPaciente(1);

        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setIdUsuario(99);

        Paciente entrada = new Paciente();
        entrada.setFkUsuario(new Usuario());

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(usuarioService.buscarEntidadePorIdOuThrow(99)).thenReturn(usuarioNovo);
        when(repository.save(existente)).thenReturn(existente);

        Paciente result = service.atualizarParcial(1, entrada, 99);

        assertEquals(usuarioNovo, result.getFkUsuario());
    }

    @Test
    @DisplayName("Deve atualizar parcialmente sem trocar usuário")
    void deveAtualizarParcialSemTrocarUsuario() {
        Paciente existente = new Paciente();
        existente.setIdPaciente(1);

        Paciente entrada = new Paciente();

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        Paciente result = service.atualizarParcial(1, entrada, null);

        assertEquals(existente.getFkUsuario(), result.getFkUsuario());
    }

    @Test
    @DisplayName("Deve deletar paciente com sucesso")
    void deveDeletarComSucesso() {
        Paciente p = new Paciente();

        when(repository.findById(4)).thenReturn(Optional.of(p));

        service.deletarPorId(4);

        verify(repository).delete(p);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar paciente inexistente")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(repository.findById(15)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.deletarPorId(15));
    }
}
