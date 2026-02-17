package flor_de_lotus.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseBody {

    @Schema(description = "Id do usuário", example = "1")
    private Integer id;

    @Schema(description = "Nome do usuário", example = "Luíza Oliveira")
    private String nome;

    @Schema(description = "Email do usuário", example = "luiza@oliveira.con")
    private String email;

    @Column(unique = true )
    @Schema(description = "Número do usuário", example = "119xxxxxxxx")
    private String telefone;

    @Schema(description = "Nível de permissão", example = "1")
    private String nivelPermissao;

}