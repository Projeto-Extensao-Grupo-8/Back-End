package flor_de_lotus.usuario.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioPostRequestBody {
    private String nome;
    @Email(message = "E-mail inválido")
    private String email;
    private String telefone;
    @CPF(message = "CPF inválido")
    private String cpf;
    private String senha;
    private String cep;
    private String numero;
    private String complemento;

}
