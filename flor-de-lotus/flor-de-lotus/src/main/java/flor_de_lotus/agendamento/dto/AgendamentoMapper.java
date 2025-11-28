package flor_de_lotus.agendamento.dto;

import flor_de_lotus.agendamento.Agendamento;
import flor_de_lotus.funcionario.Funcionario;

public class AgendamentoMapper {

    public static Agendamento of(AgendamentoPostRequest dto, Funcionario funcionario) {
        Agendamento agendamento = new Agendamento();

        agendamento.setInicioTempo(dto.getInicioTempo());
        agendamento.setFinalTempo(dto.getFinalTempo());
        agendamento.setDataDia(dto.getDataDia());
        agendamento.setFkFuncionario(funcionario);

        return agendamento;
    }

    public static Agendamento of(AgendamentoPostRequest dto) {
        Agendamento agendamento = new Agendamento();

        agendamento.setInicioTempo(dto.getInicioTempo());
        agendamento.setFinalTempo(dto.getFinalTempo());
        agendamento.setDataDia(dto.getDataDia());

        return agendamento;
    }

    public static AgendamentoResponse of(Agendamento entity) {
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
}
