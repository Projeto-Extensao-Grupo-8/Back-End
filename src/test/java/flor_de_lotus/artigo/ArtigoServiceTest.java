package flor_de_lotus.artigo;

import flor_de_lotus.artigo.Event.ArtigoCreatedEvent;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioRepository;
import flor_de_lotus.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtigoServiceTest {

    @Mock
    private ArtigoRepository artigoRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private ArtigoService artigoService;


    @Test
    @DisplayName("Deve retornar todos os artigos quando o termo for nulo ou vazio")
    void deveRetornarTodosQuandoTermoVazio() {

        when(artigoRepository.findAll()).thenReturn(List.of(new Artigo(), new Artigo()));

        List<Artigo> resultado = artigoService.pesquisar("");


        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(artigoRepository, times(1)).findAll();
        verify(artigoRepository, never()).pesquisar(anyString());
    }

    @Test
    @DisplayName("Deve pesquisar por termo quando informado")
    void devePesquisarPorTermo() {

        String termo = "Java";
        when(artigoRepository.pesquisar(termo)).thenReturn(List.of(new Artigo()));

        List<Artigo> resultado = artigoService.pesquisar(termo);

        assertEquals(1, resultado.size());
        verify(artigoRepository, times(1)).pesquisar(termo);
    }


    @Test
    @DisplayName("Deve cadastrar artigo com sucesso e publicar evento")
    void deveCadastrarArtigoComSucesso() {

        Integer idFunc = 1;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(100);
        usuario.setNome("João Tester");

        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(idFunc);
        funcionario.setFkUsuario(usuario);

        Artigo artigoParaSalvar = new Artigo();
        artigoParaSalvar.setTitulo("Teste teste");
        artigoParaSalvar.setDescricao("teste teste");
        artigoParaSalvar.setDtPublicacao(LocalDate.now());

        when(funcionarioRepository.findById(idFunc)).thenReturn(Optional.of(funcionario));
        when(artigoRepository.save(any(Artigo.class))).thenReturn(artigoParaSalvar);

        Artigo salvo = artigoService.cadastrar(artigoParaSalvar, idFunc);


        assertNotNull(salvo);

        assertEquals(funcionario, salvo.getFkFuncionario());
        verify(artigoRepository, times(1)).save(artigoParaSalvar);
        verify(publisher, times(1)).publishEvent(any(ArtigoCreatedEvent.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar com funcionário inexistente")
    void deveFalharCadastrarSemFuncionario() {

        Integer idFunc = 99;
        Artigo artigo = new Artigo();
        artigo.setTitulo("Artigo Teste");

        when(funcionarioRepository.findById(idFunc)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            artigoService.cadastrar(artigo, idFunc);
        });

        assertEquals("Funcionário não encontrado", exception.getMessage());
        verify(artigoRepository, never()).save(any());
        verify(publisher, never()).publishEvent(any());
    }


    @Test
    @DisplayName("Deve atualizar artigo existente com sucesso")
    void deveAtualizarArtigoComSucesso() {

        Integer idArtigo = 10;
        Integer idFunc = 1;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(100);
        usuario.setNome("João Tester");

        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(idFunc);
        funcionario.setFkUsuario(usuario);

        Artigo artigoExistente = new Artigo();
        artigoExistente.setIdArtigo(idArtigo);
        artigoExistente.setTitulo("Título Antigo");

        Artigo dadosAtualizados = new Artigo();
        dadosAtualizados.setTitulo("Novo Título");
        dadosAtualizados.setDescricao("Nova Descrição");

        when(funcionarioRepository.findById(idFunc)).thenReturn(Optional.of(funcionario));
        when(artigoRepository.findById(idArtigo)).thenReturn(Optional.of(artigoExistente));
        when(artigoRepository.save(any(Artigo.class))).thenReturn(artigoExistente);

        Optional<Artigo> resultado = artigoService.atualizar(idArtigo, dadosAtualizados, idFunc);

        assertTrue(resultado.isPresent());
        assertEquals("Novo Título", resultado.get().getTitulo());
        assertEquals("Nova Descrição", resultado.get().getDescricao());
        verify(artigoRepository, times(1)).save(artigoExistente);
    }

    @Test
    @DisplayName("Deve retornar vazio ao tentar atualizar artigo inexistente")
    void deveRetornarVazioAoAtualizarArtigoInexistente() {

        Integer idArtigo = 99;
        Integer idFunc = 1;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(100);

        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(idFunc);
        funcionario.setFkUsuario(usuario);

        when(funcionarioRepository.findById(idFunc)).thenReturn(Optional.of(funcionario));
        when(artigoRepository.findById(idArtigo)).thenReturn(Optional.empty());


        Optional<Artigo> resultado = artigoService.atualizar(idArtigo, new Artigo(), idFunc);


        assertTrue(resultado.isEmpty());
        verify(artigoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve chamar o repository para deletar")
    void deveDeletarArtigo() {
        artigoService.deletar(1);
        verify(artigoRepository, times(1)).deleteById(1);
    }
}