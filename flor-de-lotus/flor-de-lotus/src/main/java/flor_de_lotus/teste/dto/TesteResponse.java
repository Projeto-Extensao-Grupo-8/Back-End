package flor_de_lotus.teste.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TesteResponse {
    @Schema(description = "ID do Teste inserido pelo banco.")
    private Integer idTeste;
    @Schema(description = "Código de referência do Teste")
    private String codigo;
    @Schema(description = "Nome completo do instrumento de avaliação psicológica.")
    private String nome;
    @Schema(description = "Categoria principal do Teste (Ex: Raciocínio, Personalidade, Memória).")
    private String categoria;
    @Schema(description = "Subcategoria do Teste para maior especificidade (Ex: Memória de Trabalho, Inteligência Fluida).")
    private String subCategoria;
    @Schema(description = "Nome da Editora responsável pela publicação e validação do Teste.")
    private String editora;
    @Schema(description = "Classificação do Teste, Físico ou Digital")
    private String tipo;
    @Schema(description = "Preço de venda do Teste (em BRL).")
    private Double preco;
    @Schema(description = "Quantidade mínima de unidades do Teste para acionar notificação")
    private Integer estoqueMinimo;
    @Schema(description = "Data de validade do lote atual do material do Teste.")
    private LocalDate validade;

}
