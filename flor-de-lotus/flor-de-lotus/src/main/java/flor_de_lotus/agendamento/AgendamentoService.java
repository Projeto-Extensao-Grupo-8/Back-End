package flor_de_lotus.agendamento;

import flor_de_lotus.agendamento.dto.*;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final FuncionarioRepository funcionarioRepository;

    public AgendamentoResponse publicar(AgendamentoPostRequest dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.getIdFuncionario())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Funcionário não encontrado"));

        // Verifica sobreposição de horários
        validarDisponibilidade(funcionario.getIdFuncionario(), dto.getInicioTempo(), dto.getFinalTempo(), dto.getDataDia());

        Agendamento agendamento = AgendamentoMapper.of(dto, funcionario);
        return AgendamentoMapper.of(repository.save(agendamento));
    }

    private void validarDisponibilidade(Integer idFuncionario, LocalTime inicio, LocalTime fim, java.time.LocalDate dataDia) {
        List<Agendamento> agendamentos = repository.findByFkFuncionario_IdFuncionarioAndDataDia(idFuncionario, dataDia);

        for (Agendamento existente : agendamentos) {
            if (!(fim.isBefore(existente.getInicioTempo()) || inicio.isAfter(existente.getFinalTempo()))) {
                throw new BadRequestException("Horário indisponível para o funcionário neste dia");
            }
        }
    }

    public void remover(Integer id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Agendamento não encontrado"));
        repository.delete(agendamento);
    }

    public List<AgendamentoResponse> listarTodos() {
        return repository.findAll().stream().map(AgendamentoMapper::of).collect(Collectors.toList());
    }

    public List<AgendamentoResponse> listarPorFuncionario(Integer idFuncionario) {
        List<Agendamento> lista = repository.findByFkFuncionario_IdFuncionarioAndDataDia(idFuncionario, java.time.LocalDate.now());
        return lista.stream().map(AgendamentoMapper::of).collect(Collectors.toList());
    }
}
