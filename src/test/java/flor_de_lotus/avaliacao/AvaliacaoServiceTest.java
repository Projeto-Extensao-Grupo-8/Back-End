package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoMapper;
import flor_de_lotus.avaliacao.dto.AvaliacaoRequest;
import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioService;
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

    @Mock
    private FuncionarioService funcionarioService;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar avaliação para consulta com sucesso")
    void deveCadastrarAvaliacaoParaConsultaComSucesso() {
        // Arrange
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setFkConsulta(10);
        request.setDescricao("Boa consulta");
        request.setEstrelas(5);

        Avaliacao avaliacao = new Avaliacao();
        Consulta consulta = new Consulta();
        consulta.setIdConsulta(10);

        when(consultaService.buscarPorIdOuThrow(10)).thenReturn(consulta);
        when(repository.existsByFkConsulta_IdConsulta(10)).thenReturn(false);
        when(AvaliacaoMapper.toEntity(request, consulta, null)).thenReturn(avaliacao);
        when(repository.save(avaliacao)).thenReturn(avaliacao);

        // Act
        Avaliacao result = avaliacaoService.cadastrar(request);

        // Assert
        assertEquals(avaliacao, result);
        verify(repository).save(avaliacao);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a avaliação para consulta já existe")
    void deveLancarConflitoQuandoAvaliacaoParaConsultaJaExiste() {
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setFkConsulta(5);

        when(consultaService.buscarPorIdOuThrow(5)).thenReturn(new Consulta());
        when(repository.existsByFkConsulta_IdConsulta(5)).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> {
            avaliacaoService.cadastrar(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando consulta não encontrada")
    void deveLancarExcecaoQuandoConsultaNaoEncontrada() {
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setFkConsulta(99);

        when(consultaService.buscarPorIdOuThrow(99))
                .thenThrow(new EntidadeNaoEncontradoException("Consulta não encontrada"));

        assertThrows(EntidadeNaoEncontradoException.class, () -> {
            avaliacaoService.cadastrar(request);
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

    @Test
    @DisplayName("Deve cadastrar avaliação para funcionário com sucesso")
    void deveCadastrarAvaliacaoParaFuncionarioComSucesso() {
        // Arrange
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setFkFuncionario(20);
        request.setDescricao("Bom atendimento");
        request.setEstrelas(4);

        Avaliacao avaliacao = new Avaliacao();
        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(20);

        when(funcionarioService.buscarPorIdOuThrow(20)).thenReturn(funcionario);
        when(AvaliacaoMapper.toEntity(request, null, funcionario)).thenReturn(avaliacao);
        when(repository.save(avaliacao)).thenReturn(avaliacao);

        // Act
        Avaliacao result = avaliacaoService.cadastrar(request);

        // Assert
        assertEquals(avaliacao, result);
        verify(repository).save(avaliacao);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ambos consulta e funcionário são fornecidos")
    void deveLancarExcecaoQuandoAmbosConsultaEFuncionarioFornecidos() {
        AvaliacaoRequest request = new AvaliacaoRequest();
        request.setFkConsulta(10);
        request.setFkFuncionario(20);

        assertThrows(EntidadeConflitoException.class, () -> {
            avaliacaoService.cadastrar(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhum consulta ou funcionário é fornecido")
    void deveLancarExcecaoQuandoNenhumConsultaOuFuncionarioFornecido() {
        AvaliacaoRequest request = new AvaliacaoRequest();

        assertThrows(EntidadeConflitoException.class, () -> {
            avaliacaoService.cadastrar(request);
        });

        verify(repository, never()).save(any());
    }
}
