package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.Especialidade;
import flor_de_lotus.funcionario.TipoAtendimentoPreco;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FuncionarioPatchRequestBody {
    private String crp;
    
    private List<Especialidade> especialidades;

    private String descricao;
    private String formacaoAcademica;
    private String idiomasAtendidos;
    private boolean ativo;

    private List<TipoAtendimentoPreco> tiposAtendimento;
}
