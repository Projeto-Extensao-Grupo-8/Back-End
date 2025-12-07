package flor_de_lotus.agendamento;

import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private AgendamentoService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve publicar um agendamento com sucesso")
    void devePublicarComSucesso() {
        Funcionario func = new Funcionario();
        func.setIdFuncionario(1);

        Agendamento ag = new Agendamento();
        ag.setInicioTempo(LocalTime.of(10, 0));
        ag.setFinalTempo(LocalTime.of(11, 0));
        ag.setDataDia(LocalDate.now());

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(func));
        when(repository.findByFkFuncionario_IdFuncionarioAndDataDia(1, ag.getDataDia()))
                .thenReturn(Collections.emptyList());
        when(repository.save(ag)).thenReturn(ag);

        Agendamento result = service.publicar(ag, 1);

        assertEquals(func, result.getFkFuncionario());
        verify(repository).save(ag);
    }

    @Test
    @DisplayName("Deve lançar exceção quando funcionário não for encontrado")
    void deveLancarExcecaoQuandoFuncionarioNaoEncontrado() {
        Agendamento ag = new Agendamento();

        when(funcionarioRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.publicar(ag, 5));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário estiver indisponível")
    void deveLancarExcecaoQuandoHorarioIndisponivel() {
        Funcionario func = new Funcionario();
        func.setIdFuncionario(1);

        Agendamento existente = new Agendamento();
        existente.setInicioTempo(LocalTime.of(9, 0));
        existente.setFinalTempo(LocalTime.of(11, 0));

        Agendamento novo = new Agendamento();
        novo.setInicioTempo(LocalTime.of(10, 0));
        novo.setFinalTempo(LocalTime.of(11, 30));
        novo.setDataDia(LocalDate.now());

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(func));
        when(repository.findByFkFuncionario_IdFuncionarioAndDataDia(1, novo.getDataDia()))
                .thenReturn(List.of(existente));

        assertThrows(BadRequestException.class,
                () -> service.publicar(novo, 1));
    }

    @Test
    @DisplayName("Deve remover um agendamento com sucesso")
    void deveRemoverComSucesso() {
        Agendamento ag = new Agendamento();
        ag.setIdAgendamento(10);

        when(repository.findById(10)).thenReturn(Optional.of(ag));

        service.remover(10);

        verify(repository).delete(ag);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover agendamento inexistente")
    void deveLancarExcecaoAoRemoverAgendamentoNaoExistente() {
        when(repository.findById(22)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class,
                () -> service.remover(22));
    }

    @Test
    @DisplayName("Deve listar todos os agendamentos")
    void deveListarTodos() {
        List<Agendamento> lista = List.of(new Agendamento(), new Agendamento());
        when(repository.findAll()).thenReturn(lista);

        List<Agendamento> result = service.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Deve listar agendamentos por funcionário")
    void deveListarPorFuncionario() {
        List<Agendamento> lista = List.of(new Agendamento());

        when(repository.findByFkFuncionario_IdFuncionarioAndDataDia(eq(3), any(LocalDate.class)))
                .thenReturn(lista);

        List<Agendamento> result = service.listarPorFuncionario(3);

        assertEquals(1, result.size());
    }
}
