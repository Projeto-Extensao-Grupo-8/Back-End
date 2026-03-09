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
    private String tipo;
    private String status;
    private Integer fkFuncionario;
    private Integer fkUsuario;

    public ConsultaPostRequestBody(LocalDate dataConsulta, Double valorConsulta, String especialidade, String tipo, String status, Integer fkFuncionario, Integer fkUsuario) {
        this.dataConsulta = dataConsulta;
        this.valorConsulta = valorConsulta;
        this.especialidade = especialidade;
        this.tipo = tipo;
        this.status = status;
        this.fkFuncionario = fkFuncionario;
        this.fkUsuario = fkUsuario;
    }
}
