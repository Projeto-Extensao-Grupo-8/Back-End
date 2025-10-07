package flor_de_lotus.funcionario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioPatchRequestBody {
    private String crp;
    private String especialidade;
}
