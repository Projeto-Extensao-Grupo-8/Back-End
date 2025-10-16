package flor_de_lotus.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioTokenResponseBody {

    @Schema(description = "Id do usuário", example = "1")
    private Integer userId;

    @Schema(description = "Nome do usuário", example = "Luíza Oliveira")
    private String nome;

    @Schema(description = "Email do usuário", example = "luiza@oliveira.con")
    private String email;

    @Schema(description = "Nível de permissão", example = "1")
    private String nivelPermissao;

    @Schema(description = "Token para autorização na API")
    private String token;

}
