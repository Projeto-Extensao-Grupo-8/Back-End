package flor_de_lotus.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
public class UsuarioPostRequestBody {
    @Size(min = 1, max = 90)
    @Schema(description = "Nome do usuário", example = "Luíza Oliveira")
    private String nome;

    @Email(message = "E-mail inválido")
    @Schema(description = "Email do usuário", example = "luiza@oliveira.com")
    private String email;

    @Schema(description = "Data de nascimento do usuário", example = "2025-10-10")
    private LocalDate dataNascimento;

    @Size(min = 11, max = 11)
    @Schema(description = "Número do usuário", example = "119xxxxxxxx")
    private String telefone;

    @CPF(message = "CPF inválido")
    @Schema(description = "CPF do usuário", example = "23087793060")
    private String cpf;

    @Schema(description = "Senha do usuário para cadastro, deve conter ao menos 8 caracteres e no máximo 32, 2 letras maiúsculas e uma minúscula, 1 dígito e apenas letras e números", example = "P4sSwOrdExAmPl3")
    private String senha;

    @Schema(description = "O usuário aceita ou não receber informações do newsletter", example = "true")
    private Boolean newsletter;

    @Size(min = 8, max = 8)
    @Schema(description = "CEP do usuário", example = "01414001")
    private String cep;

    @Schema(description = "Número do endereço", example = "595")
    private String numero;

    @Schema(description = "Complemento do endereço",example = "Bloco A Apto 42")
    private String complemento;

    public UsuarioPostRequestBody(String nome, String email, String telefone, String cpf, String senha, String cep, String numero, String complemento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.senha = senha;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }
}
