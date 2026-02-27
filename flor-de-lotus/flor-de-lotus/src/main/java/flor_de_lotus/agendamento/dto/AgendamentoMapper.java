package flor_de_lotus.agendamento.dto;

import flor_de_lotus.agendamento.Agendamento;
import flor_de_lotus.funcionario.Funcionario;

import java.util.List;

public class AgendamentoMapper {

    public static Agendamento toEntity(AgendamentoPostRequest dto, Funcionario funcionario) {
        Agendamento agendamento = new Agendamento();

        agendamento.setInicioTempo(dto.getInicioTempo());
        agendamento.setFinalTempo(dto.getFinalTempo());
        agendamento.setDataDia(dto.getDataDia());
        agendamento.setFkFuncionario(funcionario);

        return agendamento;
    }

    public static Agendamento toEntity(AgendamentoPostRequest dto) {
        Agendamento agendamento = new Agendamento();

        agendamento.setInicioTempo(dto.getInicioTempo());
        agendamento.setFinalTempo(dto.getFinalTempo());
        agendamento.setDataDia(dto.getDataDia());

        return agendamento;
    }

    public static AgendamentoResponse toResponse(Agendamento entity) {
        AgendamentoResponse response = new AgendamentoResponse();

        response.setIdAgendamento(entity.getIdAgendamento());
        response.setInicioTempo(entity.getInicioTempo());
        response.setFinalTempo(entity.getFinalTempo());
        response.setDataDia(entity.getDataDia());
        response.setNomeFuncionario(
                entity.getFkFuncionario() != null ? entity.getFkFuncionario().getNome() : null
        );

        return response;
    }

    public static List<AgendamentoResponse> toResponseList(List<Agendamento> agendamentos) {
        if (agendamentos == null) {
            return null;
        }
        return agendamentos.stream()
                .map(AgendamentoMapper::toResponse)
                .toList();
    }
}
