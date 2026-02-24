package flor_de_lotus.paciente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteResponseBody {
    private Integer idPaciente;
    private boolean ativo;
    private Integer idUsuario;
    private String nomeUsuario;
}
