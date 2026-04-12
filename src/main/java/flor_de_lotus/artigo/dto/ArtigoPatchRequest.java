package flor_de_lotus.artigo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ArtigoPatchRequest {

    @Schema(description = "Título do artigo", example = "A importância da meditação na cultura oriental")
    private String titulo;

    @Schema(description = "Descrição (conteúdo) do artigo",
            example = "Neste artigo, exploramos as técnicas de meditação utilizadas nos templos japoneses...")
    private String descricao;

    @Schema(description = "Data de publicação do artigo (formato: yyyy-MM-dd)", example = "2025-10-24")
    private LocalDate dtPublicacao;

    @Schema(description = "ID do funcionário autor do artigo", example = "1")
    private Integer idFuncionario;
}
