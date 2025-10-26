package flor_de_lotus.consulta.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ConsultaPostRequestBody {
    private LocalDate dataConsulta;
    private Double valorConsulta;
    private String especialidade;
    private Integer fkFuncionario;
    private Integer fkPaciente;

    public ConsultaPostRequestBody(LocalDate dataConsulta, Double valorConsulta, String especialidade, Integer fkFuncionario, Integer fkPaciente) {
        this.dataConsulta = dataConsulta;
        this.valorConsulta = valorConsulta;
        this.especialidade = especialidade;
        this.fkFuncionario = fkFuncionario;
        this.fkPaciente = fkPaciente;
    }
}
