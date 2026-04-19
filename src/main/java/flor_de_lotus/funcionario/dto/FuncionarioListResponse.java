package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.Especialidade;
import flor_de_lotus.funcionario.TipoAtendimentoPreco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FuncionarioListResponse {
    private Integer idFuncionario;
    private String nome;
    private String email;
    private String crp;
    private List<Especialidade> especialidades;
    private LocalDate dtAdmissao;
    private boolean ativo;

    private List<TipoAtendimentoPreco> tiposAtendimento;
    private String descricao;
    private String formacaoAcademica;
    private String idiomasAtendidos;

    public FuncionarioListResponse(String crp, boolean ativo) {
        this.crp = crp;
        this.ativo = ativo;
    }
}
