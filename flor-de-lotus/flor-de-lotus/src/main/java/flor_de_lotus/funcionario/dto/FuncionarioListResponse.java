package flor_de_lotus.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class FuncionarioListResponse {
    private Integer idFuncionario;
    private String nome;
    private String crp;
    private String especialidade;
    private LocalDate dtAdmissao;
    private boolean ativo;

    // Construtor anterior para compatibilidade (se ainda for usado)
    public FuncionarioListResponse(String crp, boolean ativo) {
        this.crp = crp;
        this.ativo = ativo;
    }
}
