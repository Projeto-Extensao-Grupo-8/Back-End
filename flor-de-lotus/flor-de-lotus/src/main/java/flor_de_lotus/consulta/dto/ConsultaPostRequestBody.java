package flor_de_lotus.consulta.dto;

import flor_de_lotus.consulta.StatusConsulta;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultaPostRequestBody {
    private LocalDateTime dataConsulta;
    private Double valorConsulta;
    private String especialidade;
    private String tipo;
    private StatusConsulta status;
    private Integer fkFuncionario;
    private Integer fkUsuario;

    public ConsultaPostRequestBody(LocalDateTime dataConsulta, Double valorConsulta, String especialidade, String tipo, StatusConsulta status, Integer fkFuncionario, Integer fkUsuario) {
        this.dataConsulta = dataConsulta;
        this.valorConsulta = valorConsulta;
        this.especialidade = especialidade;
        this.tipo = tipo;
        this.status = status;
        this.fkFuncionario = fkFuncionario;
        this.fkUsuario = fkUsuario;
    }
}
