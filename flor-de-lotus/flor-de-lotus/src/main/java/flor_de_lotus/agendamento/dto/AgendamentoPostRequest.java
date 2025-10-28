package flor_de_lotus.agendamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AgendamentoPostRequest {

    @NotNull
    private LocalTime inicioTempo;

    @NotNull
    private LocalTime finalTempo;

    @NotNull
    private LocalDate dataDia;

    @NotNull
    private Integer idFuncionario;
}
