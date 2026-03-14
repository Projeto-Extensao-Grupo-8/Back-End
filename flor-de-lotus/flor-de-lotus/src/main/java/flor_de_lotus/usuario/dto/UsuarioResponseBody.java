package flor_de_lotus.usuario.dto;

import flor_de_lotus.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import java.time.LocalDate;

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

    @Schema(description = "Número do usuário", example = "119xxxxxxxx")
    private String telefone;

    @Schema(description = "Data de cadastro do usuário", example = "2025-10-10")
    private LocalDate dataCadastro;

    @Schema(description = "Data de nascimento do usuário", example = "2025-10-10")
    private LocalDate dataNascimento;

    @Schema(description = "Nível de permissão", example = "1")
    private String nivelPermissao;

    @Schema(description = "Endereço do usuário")
    private Endereco endereco;

}