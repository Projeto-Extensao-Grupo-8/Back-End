package flor_de_lotus.request;

import flor_de_lotus.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class FuncionarioPostRequestBody {
    @Column(unique = true )
    private String crp;
    private String especialidade;
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean ativo;
    @OneToOne
    private Usuario fkUsuario;
}
