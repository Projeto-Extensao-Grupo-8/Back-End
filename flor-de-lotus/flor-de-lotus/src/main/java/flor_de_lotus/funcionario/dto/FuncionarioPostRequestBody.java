package flor_de_lotus.funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioPostRequestBody {
    @NotBlank
    private String crp;
    @NotBlank
    private String especialidade;
    @NotNull
    private Integer fkUsuario;
}
