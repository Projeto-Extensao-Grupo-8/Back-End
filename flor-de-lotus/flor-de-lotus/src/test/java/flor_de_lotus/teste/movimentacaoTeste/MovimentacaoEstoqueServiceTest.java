package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimentacaoEstoqueServiceTest {

    @InjectMocks
    private MovimentacaoEstoqueService service;

    @Mock
    private MovimentacaoEstoqueRepository repository;
    @Mock
    private TesteService testeService;
    @Mock
    private ConsultaService consultaService;

    private MovimentacaoEstoque movimentacaoEnvio;
    private MovimentacaoEstoque movimentacaoDesejada;
    private Teste testeMockado;
    private Consulta consultaMockada;

    @BeforeEach
    void setUp() {

        testeMockado = new Teste();
        testeMockado.setIdTeste(10);

        consultaMockada = new Consulta();
        consultaMockada.setIdConsulta(20);
        consultaMockada.setDataConsulta(LocalDate.of(2025, 10, 20));

        movimentacaoEnvio = new MovimentacaoEstoque();
        movimentacaoEnvio.setQtd(5);

        movimentacaoDesejada = new MovimentacaoEstoque();
        movimentacaoDesejada.setIdMovimentacaoEstoque(1);
        movimentacaoDesejada.setQtd(5);
        movimentacaoDesejada.setTeste(testeMockado);
        movimentacaoDesejada.setConsulta(consultaMockada);
        movimentacaoDesejada.setDataMovimentacao(consultaMockada.getDataConsulta());
    }

    @Test
    @DisplayName("Deve cadastrar a movimentação com sucesso e associar Teste/Consulta")
    void cadastrarMovimentacaoComSucessoTest() {
        when(testeService.findByIdOrThrow(anyInt())).thenReturn(testeMockado);
        when(consultaService.buscarPorIdOuThrow(anyInt())).thenReturn(consultaMockada);
        when(repository.save(movimentacaoEnvio)).thenReturn(movimentacaoDesejada);


        MovimentacaoEstoque resultado = service.cadastrar(movimentacaoEnvio, 10, 20);


        Assertions.assertNotNull(resultado);


        verify(testeService, times(1)).findByIdOrThrow(anyInt());
        verify(consultaService, times(1)).buscarPorIdOuThrow(anyInt());
        verify(repository, times(1)).save(any(MovimentacaoEstoque.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se o ID do Teste for inválido ao cadastrar")
    void deveLancarExcecaoSeIdTesteInvalidoNoCadastro() {

        when(testeService.findByIdOrThrow(anyInt())).thenThrow(new EntidadeNaoEncontradoException("Teste não encontrado"));
//        when(consultaService.buscarPorIdOuThrow(anyInt())).thenReturn(consultaMockada);


        Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.cadastrar(movimentacaoEnvio, 99, 20));

        verify(testeService, times(1)).findByIdOrThrow(anyInt());
        verify(repository, never()).save(any());
    }


    @Test
    @DisplayName("Deve retornar uma lista de movimentações cheia")
    void deveListarTodasAsMovimentacoesTest() {
        List<MovimentacaoEstoque> listaMockada = List.of(movimentacaoDesejada, new MovimentacaoEstoque());
        when(repository.findAll()).thenReturn(listaMockada);

        List<MovimentacaoEstoque> resultado = service.listar();

        Assertions.assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista de movimentações vazia")
    void deveRetornarListaVaziaDeMovimentacoesTest() {
        when(repository.findAll()).thenReturn(List.of());

        List<MovimentacaoEstoque> resultado = service.listar();

        Assertions.assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar por ID e retornar a movimentação")
    void buscarPorIdComSucessoTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(movimentacaoDesejada));

        MovimentacaoEstoque resultado = service.buscarPorIdOuThrow(1);

        Assertions.assertNotNull(resultado);
        verify(repository, times(1)).findById(anyInt());
    }



    @Test
    @DisplayName("Deve listar movimentações por ID de Paciente com sucesso")
    void listarPorPacienteComSucessoTest() {
        Consulta consulta1 = new Consulta(); consulta1.setIdConsulta(20);
        Consulta consulta2 = new Consulta(); consulta2.setIdConsulta(21);
        List<Consulta> listaConsultas = List.of(consulta1, consulta2);

        MovimentacaoEstoque movi1 = new MovimentacaoEstoque();
        MovimentacaoEstoque movi2 = new MovimentacaoEstoque();
        List<MovimentacaoEstoque> listaMovimentacao =List.of(movi1, movi2);

        when(consultaService.listarPorPaciente(anyInt())).thenReturn(listaConsultas);
        when(repository.findAllByConsultaIdConsulta(anyInt())).thenReturn(listaMovimentacao);

        List<MovimentacaoEstoque> resultado = service.listarPorPaciente(100);


        Assertions.assertEquals(4, resultado.size());
        verify(consultaService, times(1)).listarPorPaciente(anyInt());

    }

    @Test
    @DisplayName("Deve listar movimentações por ID de Funcionário com sucesso")
    void listarPorFuncionarioComSucessoTest() {
        Consulta consulta1 = new Consulta(); consulta1.setIdConsulta(30);
        List<Consulta> listaConsultas = List.of(consulta1);

        MovimentacaoEstoque movi1 = new MovimentacaoEstoque();


        when(consultaService.listarPorFuncionario(anyInt())).thenReturn(listaConsultas);


        when(repository.findAllByConsultaIdConsulta(anyInt())).thenReturn(List.of(movi1));


        List<MovimentacaoEstoque> resultado = service.listarPorFuncionario(200);


        Assertions.assertEquals(1, resultado.size());
        verify(consultaService, times(1)).listarPorFuncionario(anyInt());
        verify(repository, times(1)).findAllByConsultaIdConsulta(anyInt());
    }

    @Test
    @DisplayName("Deve atualizar MovimentacaoEstoque com todos os campos (Qtd, Descricao, Teste, Consulta)")
    void atualizarParcialComTodosCamposTest() {
        MovimentacaoEstoque moviOriginal = new MovimentacaoEstoque();
        moviOriginal.setIdMovimentacaoEstoque(1);
        moviOriginal.setQtd(1);
        moviOriginal.setDescricao("Antiga Descrição");


        Teste novoTeste = new Teste(); novoTeste.setIdTeste(50);
        Consulta novaConsulta = new Consulta(); novaConsulta.setIdConsulta(60);


        MovimentacaoEstoque entityUpdate = new MovimentacaoEstoque();
        entityUpdate.setQtd(10);
        entityUpdate.setDescricao("Nova Descrição");


        when(repository.findById(anyInt())).thenReturn(Optional.of(moviOriginal));


        when(testeService.findByIdOrThrow(anyInt())).thenReturn(novoTeste);
        when(consultaService.buscarPorIdOuThrow(anyInt())).thenReturn(novaConsulta);


        when(repository.save(any(MovimentacaoEstoque.class))).thenAnswer(i -> i.getArguments()[0]);


        MovimentacaoEstoque resultado = service.atualizarParcial(1, entityUpdate, 60, 50);


        Assertions.assertEquals(10, resultado.getQtd());


        verify(repository, times(1)).findById(anyInt());
        verify(testeService, times(1)).findByIdOrThrow(anyInt());
        verify(consultaService, times(1)).buscarPorIdOuThrow(anyInt());
        verify(repository, times(1)).save(any(MovimentacaoEstoque.class));
    }

    @Test
    @DisplayName("Deve atualizar MovimentacaoEstoque apenas Qtd e Descricao (FKs nulas)")
    void atualizarParcialApenasCamposBaseTest() {
        MovimentacaoEstoque moviOriginal = new MovimentacaoEstoque();
        moviOriginal.setIdMovimentacaoEstoque(1);
        moviOriginal.setQtd(1);


        MovimentacaoEstoque entityUpdate = new MovimentacaoEstoque();
        entityUpdate.setQtd(10);


        when(repository.findById(anyInt())).thenReturn(Optional.of(moviOriginal));


        when(repository.save(any(MovimentacaoEstoque.class))).thenAnswer(i -> i.getArguments()[0]);


        MovimentacaoEstoque resultado = service.atualizarParcial(1, entityUpdate, null, null);


        Assertions.assertEquals(10, resultado.getQtd());


        verify(repository, times(1)).findById(anyInt());

        verify(repository, times(1)).save(any(MovimentacaoEstoque.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se ID da Movimentação for inválido ao atualizar")
    void deveLancarExcecaoSeIdMovimentacaoInvalidoNaAtualizacaoTest() {

        when(repository.findById(anyInt())).thenReturn(Optional.empty());


        Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.atualizarParcial(999, new MovimentacaoEstoque(), null, null));


        verify(repository, times(1)).findById(anyInt());
        verify(repository, never()).save(any());
    }
}

