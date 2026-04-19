package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.Especialidade;
import flor_de_lotus.funcionario.Modalidade;
import flor_de_lotus.funcionario.TipoAtendimentoPreco;
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
    
    private List<Especialidade> especialidades;
    
    private Modalidade modalidade;

    private String descricao;
    private String formacaoAcademica;
    private String idiomasAtendidos;
    @NotNull
    private Integer fkUsuario;

    private List<TipoAtendimentoPreco> tiposAtendimento;
}
