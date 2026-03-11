package flor_de_lotus.consulta.dto;

import flor_de_lotus.consulta.StatusConsulta;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ConsultaResponseBody {
    private Integer idConsulta;
    private LocalDate dataConsulta;
    private Double valorConsulta;
    private String especialidade;
    private String tipo;
    private StatusConsulta status;
    private String nomeFuncionario;
    private String nomePaciente;
}
