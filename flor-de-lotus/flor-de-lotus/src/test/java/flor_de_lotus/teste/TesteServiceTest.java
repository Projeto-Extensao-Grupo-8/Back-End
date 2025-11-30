package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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

        verify(repository.existsByCodigo(any(String.class)), times(1));
        verify(repository.save(any(Teste.class)), times(1));

    }

    @Test
    void deveRetornarExceptionAoTentarCadastrarTesteExistente() {
        when(repository.existsByCodigo(testeEnvio.getCodigo())).thenReturn(true);

        EntidadeConflitoException exceptionResultado = Assertions.assertThrows(EntidadeConflitoException.class,
                ()-> {service.cadastrar(testeEnvio);
        });


        Assertions.assertEquals("Teste já cadastrado", exceptionResultado.getMessage());

        verify(repository.existsByCodigo(any(String.class)), times(1));
        verify(repository.save(any(Teste.class)), times(0));
        verify(repository.save(any(Teste.class)), never());
    }

    @Test
    void findByIdOrThrowResponse() {

    }

    @Test
    void findByIdOrThrow() {
    }

    @Test
    void deletarPorId() {
    }

    @Test
    void listarTodos() {
    }

    @Test
    void listarPorCategoria() {
    }

    @Test
    void listarPorTipo() {
    }

    @Test
    void buscarPorValidade() {
    }

    @Test
    void testBuscarPorValidade() {
    }
}