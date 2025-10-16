package flor_de_lotus.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioLoginRequestBody {

    @Email(message = "E-mail inválido")
    @Schema(description = "Email do usuário para login", example = "luiza@oliveira.com")
    private String email;

    @Schema(description = "Senha do usuário para login", example = "P4sSwOrdExAmPl3")
    private String senha;

}
