package flor_de_lotus.artigo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ArtigoResponse {

    @Schema(description = "ID do Artigo inserido pelo banco.")
    private Integer idArtigo;

    @Schema(description = "Título do artigo")
    private String titulo;

    @Schema(description = "Descrição (conteúdo ou resumo) do artigo")
    private String descricao;

    @Schema(description = "Data de publicação do artigo (yyyy-MM-dd)")
    private LocalDate dtPublicacao;

    @Schema(description = "ID do funcionário autor do artigo")
    private Integer idFuncionario;
}