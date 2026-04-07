package flor_de_lotus.artigo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ArtigoPostRequest {

    @NotBlank
    @Schema(description = "Título do artigo", example = "A importância da meditação na cultura oriental")
    private String titulo;

    @NotBlank
    @Schema(description = "Descrição (conteúdo) do artigo",
            example = "Neste artigo, exploramos as técnicas de meditação utilizadas nos templos japoneses...")
    private String descricao;

    @NotNull
    @Schema(description = "Data de publicação do artigo (formato: yyyy-MM-dd)", example = "2025-10-24")
    private LocalDate dtPublicacao;

    @NotNull
    @Schema(description = "ID do funcionário autor do artigo", example = "1")
    private Integer idFuncionario;
}