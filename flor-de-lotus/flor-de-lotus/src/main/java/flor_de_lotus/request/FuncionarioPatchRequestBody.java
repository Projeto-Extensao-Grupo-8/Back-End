package flor_de_lotus.request;

import flor_de_lotus.entity.Endereco;
import flor_de_lotus.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioPatchRequestBody {
    private String crp;
    private String especialidade;
}
