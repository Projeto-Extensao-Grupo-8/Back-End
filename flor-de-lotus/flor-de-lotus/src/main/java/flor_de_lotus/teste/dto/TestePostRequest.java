package flor_de_lotus.teste.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestePostRequest {
    @NotBlank
    @Schema(description = "Código de referência do Teste",
            example = "T-MEMORIA-001")
    private String codigo;
    @NotBlank
    @Schema(description = "Nome completo do instrumento de avaliação psicológica.",
            example = "Teste de Memória para Adultos")
    private String nome;
    @NotBlank
    @Schema(description = "Categoria principal do Teste (Ex: Raciocínio, Personalidade, Memória).",
            example = "Memória")
    private String categoria;
    @NotBlank
    @Schema(description = "Subcategoria do Teste para maior especificidade (Ex: Memória de Trabalho, Inteligência Fluida)."
            , example = "Memória de Trabalho")
    private String subCategoria;
    @NotBlank
    @Schema(
            description = "Nome da Editora responsável pela publicação e validação do Teste.",
            example = "Vetor Editora"
    )
    private String editora;
    @NotBlank
    @Schema(
            description = "Classificação do Teste, Físico ou Digital",
            example = "Digital"
    )
    private String tipo;
    @NotNull
    @PositiveOrZero
    @Schema(
            description = "Preço de venda do Teste (em BRL).",
            example = "150.75")
    private Double preco;
    @NotNull
    @PositiveOrZero
    private Integer estoqueMinimo;
    @NotNull
    @FutureOrPresent
    @Schema(
            description = "Data de validade do lote atual do material do Teste. Relevante para testes com validade regulamentada.",
            example = "2026-12-31"
    )
    private LocalDate validade;


}
