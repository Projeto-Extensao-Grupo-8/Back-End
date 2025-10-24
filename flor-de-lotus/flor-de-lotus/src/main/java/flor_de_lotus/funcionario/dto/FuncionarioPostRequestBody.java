package flor_de_lotus.funcionario.dto;

import flor_de_lotus.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioPostRequestBody {
    @Column(unique = true )
    private String crp;
    private String especialidade;
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean ativo;
    @OneToOne
    private Integer fkUsuario;
}
