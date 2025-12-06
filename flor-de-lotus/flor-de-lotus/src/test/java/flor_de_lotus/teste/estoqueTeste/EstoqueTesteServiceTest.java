package flor_de_lotus.teste.estoqueTeste;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueTesteServiceTest {

        @Mock
        private EstoqueTesteRespository respository;

        @Mock
        private TesteService testeservice;

        @InjectMocks
        private EstoqueTesteService service;

        private Teste testeMockado;
        private EstoqueTeste estoqueEnvio;
        private EstoqueTeste estoqueDesejado;

        @BeforeEach
        void setUp() {
            testeMockado = new Teste();
            testeMockado.setIdTeste(10);
            testeMockado.setNome("Teste TDAH");

            estoqueEnvio = new EstoqueTeste();
            estoqueEnvio.setQtdAtual(50);

            estoqueDesejado = new EstoqueTeste();
            estoqueDesejado.setIdEstoqueTeste(1);
            estoqueDesejado.setQtdAtual(50);
            estoqueDesejado.setDtReferencia(LocalDate.now());
            estoqueDesejado.setFkTeste(testeMockado);
        }

        @Test
        @DisplayName("Deve cadastrar o EstoqueTeste com sucesso e associar o Teste")
        void cadastrarEstoqueTesteComSucessoTest() {
            when(testeservice.findByIdOrThrow(anyInt())).thenReturn(testeMockado);
            when(respository.save(any(EstoqueTeste.class))).thenReturn(estoqueDesejado);

            EstoqueTeste resultado = service.cadastrar(estoqueEnvio, 10);

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(LocalDate.now(), resultado.getDtReferencia());

            verify(testeservice, times(1)).findByIdOrThrow(anyInt());
            verify(respository, times(1)).save(any(EstoqueTeste.class));
        }

        @Test
        @DisplayName("Deve lançar exceção se o ID do Teste for inválido ao cadastrar")
        void deveLancarExcecaoSeIdTesteInvalidoNoCadastro() {
            when(testeservice.findByIdOrThrow(anyInt())).thenThrow(new EntidadeNaoEncontradoException("Teste não encontrado"));

            Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                    () -> service.cadastrar(estoqueEnvio, 99));

            verify(testeservice, times(1)).findByIdOrThrow(anyInt());
            verify(respository, never()).save(any());
        }

        @Test
        @DisplayName("Deve buscar por ID e retornar o EstoqueTeste")
        void buscarPorIdComSucessoTest() {
            when(respository.findById(anyInt())).thenReturn(Optional.of(estoqueDesejado));

            EstoqueTeste resultado = service.findByIdOrThrow(1);

            Assertions.assertEquals(estoqueDesejado.getIdEstoqueTeste(), resultado.getIdEstoqueTeste());
            verify(respository, times(1)).findById(anyInt());
        }

        @Test
        @DisplayName("Deve lançar exceção se ID não for encontrado ao buscar")
        void deveLancarExcecaoSeIdInvalidoAoBuscarTest() {
            when(respository.findById(anyInt())).thenReturn(Optional.empty());

            EntidadeNaoEncontradoException exception = Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                    () -> service.findByIdOrThrow(99));

            Assertions.assertEquals("EstoqueTeste não encontrado", exception.getMessage());
            verify(respository, times(1)).findById(anyInt());
        }

        @Test
        @DisplayName("Deve atualizar EstoqueTeste parcial, mudando Qtd e FK Teste")
        void atualizarParcialComSucessoTest() {
            EstoqueTeste estoqueOriginal = estoqueDesejado;
            Teste novoTeste = new Teste();
            novoTeste.setIdTeste(20);

            EstoqueTeste entityUpdate = new EstoqueTeste();
            entityUpdate.setQtdAtual(100);

            when(respository.findById(anyInt())).thenReturn(Optional.of(estoqueOriginal));
            when(testeservice.findByIdOrThrow(anyInt())).thenReturn(novoTeste);
            when(respository.save(any(EstoqueTeste.class))).thenAnswer(i -> i.getArguments()[0]);

            EstoqueTeste resultado = service.atualizarParcial(1, entityUpdate, 20);

            Assertions.assertEquals(100, resultado.getQtdAtual());
            Assertions.assertEquals(novoTeste, resultado.getFkTeste());

            verify(respository, times(1)).findById(anyInt());
            verify(testeservice, times(1)).findByIdOrThrow(anyInt());
            verify(respository, times(1)).save(any(EstoqueTeste.class));
        }

        @Test
        @DisplayName("Deve atualizar EstoqueTeste parcial, mantendo FK Teste e mudando apenas Qtd")
        void atualizarParcialApenasQtdTest() {
            EstoqueTeste estoqueOriginal = estoqueDesejado;
            estoqueOriginal.setFkTeste(testeMockado);
            when(testeservice.findByIdOrThrow(anyInt())).thenReturn(testeMockado);

            EstoqueTeste entityUpdate = new EstoqueTeste();
            entityUpdate.setQtdAtual(100);

            when(respository.findById(anyInt())).thenReturn(Optional.of(estoqueOriginal));
            when(respository.save(any(EstoqueTeste.class))).thenAnswer(i -> i.getArguments()[0]);

            EstoqueTeste resultado = service.atualizarParcial(1, entityUpdate, 10);

            Assertions.assertEquals(100, resultado.getQtdAtual());
            Assertions.assertEquals(testeMockado, resultado.getFkTeste());

            verify(respository, times(1)).findById(anyInt());
            verify(testeservice, times(1)).findByIdOrThrow(anyInt());
            verify(respository, times(1)).save(any(EstoqueTeste.class));
        }

        @Test
        @DisplayName("Deve lançar exceção se ID do Estoque for inválido ao atualizar")
        void deveLancarExcecaoSeIdEstoqueInvalidoNaAtualizacaoTest() {
            when(respository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntidadeNaoEncontradoException.class,
                    () -> service.atualizarParcial(999, new EstoqueTeste(), 10));

            verify(respository, times(1)).findById(anyInt());
            verify(testeservice, never()).findByIdOrThrow(anyInt());
            verify(respository, never()).save(any());
        }

        @Test
        @DisplayName("Deve listar todos os Estoques (lista cheia)")
        void listarTodosTest() {
            List<EstoqueTeste> listaMockada = List.of(estoqueDesejado, new EstoqueTeste());
            when(respository.findAll()).thenReturn(listaMockada);

            List<EstoqueTeste> resultado = this.service.listarTodos();

            Assertions.assertEquals(2, resultado.size());
            verify(respository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar lista vazia ao listar todos")
        void listarTodosRetornaListaVaziaTest() {
            when(respository.findAll()).thenReturn(List.of());

            List<EstoqueTeste> resultado = this.service.listarTodos();

            Assertions.assertTrue(resultado.isEmpty());
            verify(respository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar o EstoqueTeste com a menor QtdAtual")
        void buscarPorQtdComSucessoTest() {
            when(respository.findTop1ByOrderByQtdAtualAsc()).thenReturn(Optional.of(estoqueDesejado));

            EstoqueTeste resultado = this.service.buscarPorQtd();

            Assertions.assertEquals(estoqueDesejado.getQtdAtual(), resultado.getQtdAtual());
            verify(respository, times(1)).findTop1ByOrderByQtdAtualAsc();
        }


    }
