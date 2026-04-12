package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.TipoAtendimentoPreco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioResponse {
    private Integer idFuncionario;
    private String crp;
    private LocalDate dtAdmissao;
    private boolean ativo;
    private Integer idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private List<TipoAtendimentoPreco> tiposAtendimento;
    private String especialidade;
    private String descricao;
    private String formacaoAcademica;
    private String idiomasAtendidos;

}
