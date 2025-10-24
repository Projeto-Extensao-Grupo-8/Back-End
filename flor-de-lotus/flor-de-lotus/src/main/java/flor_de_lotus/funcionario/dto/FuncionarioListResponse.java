package flor_de_lotus.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FuncionarioListResponse {
    private String crp;
    private boolean ativo;
}
