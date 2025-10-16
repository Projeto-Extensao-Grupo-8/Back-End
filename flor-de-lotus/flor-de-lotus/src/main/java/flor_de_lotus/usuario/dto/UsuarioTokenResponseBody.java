package flor_de_lotus.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioTokenResponseBody {

    private Integer userId;
    private String nome;
    private String email;

    @Schema(description = "Nível de permissão", example = "1")
    private String nivelPermissao;

    private String token;

}
