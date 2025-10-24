package flor_de_lotus.paciente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteResponseBody {
    private Integer idPaciente;
    private Character ativo;
    private Integer idUsuario;
    private String nomeUsuario;
}
