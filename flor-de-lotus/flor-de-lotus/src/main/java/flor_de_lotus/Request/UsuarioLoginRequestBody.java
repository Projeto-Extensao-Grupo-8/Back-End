package flor_de_lotus.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioLoginRequestBody {
    @Email(message = "E-mail inválido")
    private String email;
    private String senha;
}
