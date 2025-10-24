package flor_de_lotus.consulta.dto;

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
    private String nomeFuncionario;
    private String nomePaciente;
}
