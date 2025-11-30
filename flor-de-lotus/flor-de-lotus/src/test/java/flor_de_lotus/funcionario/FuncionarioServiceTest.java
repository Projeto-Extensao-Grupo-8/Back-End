package flor_de_lotus.funcionario;

import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @InjectMocks
    private UsuarioService serviceU;

    @InjectMocks
    private FuncionarioService service;

    @Nested
    @DisplayName("Testes do método cadastrar")
    class metodoCadastrarTest{

        //@ParameterizedTest
        @DisplayName("Adicionar o usuário com sucesso")
        void deveAdicionarComSucessoTest() {

            Endereco endereco = new Endereco();

            Usuario usuario = new Usuario(1,"rob","teste@gmail.com", "11966696734","42396577840",true,"123456789","4",endereco);

            Mockito.when(serviceU.buscarEntidadePorIdOuThrow(usuario.getIdUsuario())).thenReturn(usuario);

            Usuario usuarioEsperado = serviceU.buscarEntidadePorIdOuThrow(usuario.getIdUsuario());

            Mockito.when(repository.existsByCrp(any(String.class)));


        }

    }

    @Nested
    @DisplayName("Testes do método ListarTodos")
    class metodoListarTodosTest{

        @Test
        @DisplayName("Quando a tabela estiver vazia, deve retornar a lista vazia")
        void deveRetornarListaVaziaTest(){

            List<Funcionario> esperado = new ArrayList<>();

            Mockito.when(repository.findAll()).thenReturn(esperado);

            List<Funcionario> recebido = service.listarTodos();

            Assertions.assertTrue(recebido.isEmpty());

        }

        @Test
        @DisplayName("Quando a tabela estiver com conteúdo, deve retornar a lista")
        void deveRetornarListaComConteúdoTest(){

            List<Funcionario> esperado = new ArrayList<>();

            Funcionario func = new Funcionario();

            esperado.add(func);

            Mockito.when(repository.findAll()).thenReturn(esperado);

            List<Funcionario> recebido = service.listarTodos();

            Assertions.assertFalse(recebido.isEmpty());

        }

    }

}