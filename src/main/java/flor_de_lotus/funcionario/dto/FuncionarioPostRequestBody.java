package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.TipoAtendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FuncionarioPostRequestBody {
    @NotBlank
    private String crp;
    @NotBlank
    private String especialidade;
    private String descricao;
    private String formacaoAcademica;
    private String idiomasAtendidos;
    @NotNull
    private Integer fkUsuario;

    private List<TipoAtendimento> tiposAtendimento;
}
