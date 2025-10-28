package flor_de_lotus.agendamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AgendamentoResponse {
    private Integer idAgendamento;
    private LocalTime inicioTempo;
    private LocalTime finalTempo;
    private LocalDate dataDia;
    private String nomeFuncionario;
}
