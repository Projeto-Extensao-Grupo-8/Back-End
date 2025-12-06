package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TesteServiceTest {

    @Mock
    public TesteRepository repository;

    @InjectMocks
    public TesteService service;

    private Teste testeDesejado;
    private Teste testeEnvio;

    @BeforeEach
    void setUp(){
        testeDesejado = new Teste();
        testeDesejado.setIdTeste(1);
        testeDesejado.setSubCategoria("XPTO");
        testeDesejado.setNome("Teste TDAH");
        testeDesejado.setCategoria("TDAH");
        testeDesejado.setCodigo("WXH-00");
        testeDesejado.setEditora("ACHÉ");
        testeDesejado.setEstoqueMinimo(10);
        testeDesejado.setTipo("Teste Digital");
        testeDesejado.setPreco(200.00);
        testeDesejado.setValidade(LocalDate.now());

        testeEnvio = new Teste();
        testeEnvio.setSubCategoria("XPTO");
        testeEnvio.setNome("Teste TDAH");
        testeEnvio.setCategoria("TDAH");
        testeEnvio.setCodigo("WXH-00");
        testeEnvio.setEditora("ACHÉ");
        testeEnvio.setEstoqueMinimo(10);
        testeEnvio.setTipo("Teste Digital");
        testeEnvio.setPreco(200.00);
        testeEnvio.setValidade(LocalDate.now());
    }

    @Test
    void cadastrarTesteComSucessoTest() {
        when(repository.existsByCodigo(testeEnvio.getCodigo())).thenReturn(false);
        when(repository.save(testeEnvio)).thenReturn(testeDesejado);

        Teste testeResultado = service.cadastrar(testeEnvio);
        Assertions.assertEquals(testeDesejado.getIdTeste(), testeResultado.getIdTeste());
        Assertions.assertEquals(testeDesejado.getCodigo(), testeResultado.getCodigo());

        verify(repository, times(1)).existsByCodigo(any(String.class));
        verify(repository, times(1)).save(any(Teste.class));

    }

    @Test
    @DisplayName("Deve buscar por Id quando acionado com Id invalido deve lançar exceção")
    void deveRetornarExceptionAoTentarCadastrarTesteExistente() {
        when(repository.existsByCodigo(testeEnvio.getCodigo())).thenReturn(true);

        EntidadeConflitoException exceptionResultado = Assertions.assertThrows(EntidadeConflitoException.class,
                ()-> {service.cadastrar(testeEnvio);
        });


        Assertions.assertEquals("Teste já cadastrado", exceptionResultado.getMessage());

        verify(repository, times(1)).existsByCodigo(any(String.class));
        verify(repository, times(0)).save(any(Teste.class));
        verify(repository, never()).save(any(Teste.class));
    }


    @Test
    @DisplayName("Deve buscar por Id quando acionado com Id valido deve devolver teste")
    void findByIdOrThrow() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(testeEnvio));
        Teste resultado = this.service.findByIdOrThrow(1);
        Assertions.assertEquals(testeEnvio.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Deve buscar por Id quando acionado com Id invalido deve lançar exceção")
    void deveLancarExcecaoQuandoIdForInvalido() {
        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        EntidadeNaoEncontradoException exception = Assertions.assertThrows(EntidadeNaoEncontradoException.class, () -> this.service.findByIdOrThrow(200), "Devera lançar exceção se o id não existir");
        Assertions.assertEquals("Teste não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve trazer a lista cheia")
    void deveListarTudoTest() {
        List<Teste> listaMockada = List.of(new Teste());
        Mockito.when(repository.findAll()).thenReturn(listaMockada);
        List<Teste> resultado = this.service.listarTodos();
        Assertions.assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve retornar a lista de empresas vazia")
    void deveRetornarListaVaziaTest() {
        Mockito.when(this.repository.findAll()).thenReturn(List.of());
        List<Teste> resultado = this.service.listarTodos();
        Assertions.assertTrue(resultado.isEmpty());
    }

    // CORREÇÃO PARA O MÉTODO "deveLancarExcecaoEntidadeNaoEncontradaExceptionSeNaoEncontrarTest"

    @Test
    @DisplayName("Deve lançar exceção se tentar excluir e nao existir id")
    void deveLancarExcecaoEntidadeNaoEncontradaExceptionSeNaoEncontrarTest() {
        final Integer ID_INVALIDO = 200;

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        EntidadeNaoEncontradoException exception = Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                () -> this.service.deletarPorId(ID_INVALIDO), "Devera lançar exceção se o id não existir");


        Assertions.assertEquals("Teste não encontrado", exception.getMessage());
        verify(repository, never()).delete(any(Teste.class));
    }



    @Test
    @DisplayName("Deve trazer a lista de testes cheia por categoria")
    void deveListarPorCategoriaTest() {
        Teste testeComCategoria = new Teste();
        testeComCategoria.setCategoria("TDAH");

        List<Teste> listaMockada = List.of(testeComCategoria, testeDesejado);
        Mockito.when(repository.findByCategoria(Mockito.anyString())).thenReturn(listaMockada);

        List<Teste> resultado = this.service.listarPorCategoria("TDAH");

        Assertions.assertEquals(2, resultado.size());
        verify(repository, times(1)).findByCategoria(anyString());
    }

    @Test
    @DisplayName("Deve retornar a lista de testes vazia por categoria")
    void deveRetornarListaVaziaPorCategoriaTest() {
        Mockito.when(this.repository.findByCategoria(Mockito.anyString())).thenReturn(List.of());
        List<Teste> resultado = this.service.listarPorCategoria("NaoExistente");
        Assertions.assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findByCategoria(anyString());
    }

    @Test
    @DisplayName("Deve trazer a lista de testes cheia por tipo")
    void deveListarPorTipoTest() {
        Teste testeComTipo = new Teste();
        testeComTipo.setTipo("Teste Digital");
        List<Teste> listaMockada = List.of(testeComTipo, testeDesejado);
        Mockito.when(repository.findByTipo(Mockito.anyString())).thenReturn(listaMockada);

        List<Teste> resultado = this.service.listarPorTipo("Teste Digital");

        Assertions.assertEquals(2, resultado.size());
        verify(repository, times(1)).findByTipo(anyString());
    }

    @Test
    @DisplayName("Deve retornar a lista de testes vazia por tipo")
    void deveRetornarListaVaziaPorTipoTest() {
        Mockito.when(this.repository.findByTipo(Mockito.anyString())).thenReturn(List.of());

        List<Teste> resultado = this.service.listarPorTipo("NaoExistente");
        Assertions.assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findByTipo(anyString());
    }

    @Test
    @DisplayName("Deve retornar a contagem de testes que vencem no mes atual")
    void contarTestesAVencerNoMesAtualTest() {
        Integer esperado = 3;

        when(repository.countByValidadeBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(esperado);

        Integer resultado = this.service.buscarQtdTesteComValidadeProxima();
        Assertions.assertEquals(esperado, resultado);

        verify(repository, times(1)).countByValidadeBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Deve retornar 0 se não houver testes com validade no mes atual")
    void contarTestesAVencerNoMesAtualRetornaZeroTest() {
        Integer esperado = 0;

        when(repository.countByValidadeBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(esperado);
        Integer resultado = this.service.buscarQtdTesteComValidadeProxima();

        Assertions.assertEquals(esperado, resultado);
        verify(repository, times(1)).countByValidadeBetween(any(LocalDate.class), any(LocalDate.class));
    }
}