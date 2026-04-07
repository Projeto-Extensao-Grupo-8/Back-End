package flor_de_lotus.paciente.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteCadastroRequest {
    @NotNull
    private Integer fkUsuario;
}

