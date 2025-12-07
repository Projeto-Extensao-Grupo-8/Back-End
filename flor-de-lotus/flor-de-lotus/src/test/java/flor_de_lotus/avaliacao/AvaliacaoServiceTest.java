package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoMapper;
import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository repository;

    @Mock
    private ConsultaService consultaService;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar avaliação com sucesso")
    void deveCadastrarAvaliacaoComSucesso() {
        // Arrange
        Avaliacao avaliacao = new Avaliacao();
        Consulta consulta = new Consulta();
        consulta.setIdConsulta(10);

        when(consultaService.buscarPorIdOuThrow(10)).thenReturn(consulta);
        when(repository.existsById(10)).thenReturn(false);
        when(repository.save(avaliacao)).thenReturn(avaliacao);

        // Act
        Avaliacao result = avaliacaoService.cadastrar(avaliacao, 10);

        // Assert
        assertEquals(consulta, result.getFkConsulta());
        verify(repository).save(avaliacao);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a avaliação já existe")
    void deveLancarConflitoQuandoAvaliacaoJaExiste() {
        Avaliacao avaliacao = new Avaliacao();

        when(consultaService.buscarPorIdOuThrow(5)).thenReturn(new Consulta());
        when(repository.existsById(5)).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> {
            avaliacaoService.cadastrar(avaliacao, 5);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando consulta não encontrada")
    void deveLancarExcecaoQuandoConsultaNaoEncontrada() {
        Avaliacao avaliacao = new Avaliacao();

        when(consultaService.buscarPorIdOuThrow(99))
                .thenThrow(new EntidadeNaoEncontradoException("Consulta não encontrada"));

        assertThrows(EntidadeNaoEncontradoException.class, () -> {
            avaliacaoService.cadastrar(avaliacao, 99);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todas as avaliações")
    void deveListarTodos() {
        List<Avaliacao> lista = Arrays.asList(new Avaliacao(), new Avaliacao());

        when(repository.findAll()).thenReturn(lista);

        List<Avaliacao> result = avaliacaoService.listarTodos();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        Avaliacao a = new Avaliacao();
        a.setIdAvaliacao(1);

        when(repository.findById(1)).thenReturn(Optional.of(a));

        Avaliacao result = avaliacaoService.buscarPorIdOuThrow(1);

        assertEquals(1, result.getIdAvaliacao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar avaliação inexistente")
    void deveLancarExcecaoQuandoAvaliacaoNaoEncontrada() {
        when(repository.findById(50)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> {
            avaliacaoService.buscarPorIdOuThrow(50);
        });
    }
}
