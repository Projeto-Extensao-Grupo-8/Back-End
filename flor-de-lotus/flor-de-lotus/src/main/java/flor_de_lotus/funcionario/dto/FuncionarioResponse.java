package flor_de_lotus.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioResponse {
    private Integer idFuncionario;
    private String crp;
    private String especialidade;
    private LocalDate dtAdmissao;
    private boolean ativo;
    private Integer idUsuario;
    private String nomeUsuario;
}
