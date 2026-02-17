package flor_de_lotus.usuario.dto;

import flor_de_lotus.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioReplaceRequestBody {

    @Schema(description = "Id do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome do usuário", example = "Luíza Oliveira")
    private String nome;

    @Schema(description = "Email do usuário", example = "luiza@oliveira.con")
    @Email(message = "E-mail inválido")
    private String email;

    @Schema(description = "Data de nascimento do usuário", example = "2025-10-10")
    private LocalDate dataNascimento;

    @Schema(description = "Número do usuário", example = "119xxxxxxxx")
    private String telefone;

    @Schema(description = "CPF do usuário", example = "23087793060")
    @CPF(message = "CPF inválido")
    private String cpf;

    @Schema(description = "Senha do usuário para cadastro, deve conter ao menos 8 caracteres e no máximo 32, 2 letras maiúsculas e uma minúscula, 1 dígito e apenas letras e números", example = "P4sSwOrdExAmPl3")
    private String senha;

    @Schema(description = "O usuário aceita ou não receber informações do newsletter", example = "true")
    private Boolean newsletter;

    @Schema(description = "Fk relacionado ao endereço", example = "1")
    private Endereco fkEndereco;

}
