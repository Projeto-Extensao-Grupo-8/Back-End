package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class EstoqueTesteRequest {
    @NotBlank
    private Integer qtdAtual;
    @NotBlank
    private LocalDate dtReferencia;
    @NotBlank
    private Integer idTeste;
    @NotBlank
    private String codigo;
    @NotBlank
    private String nome;
    @NotBlank
    private String categoria;
    @NotBlank
    private String subCategoria;
    @NotBlank
    private String editora;
    @NotBlank
    private String tipo;
    @NotBlank
    private Double preco;
    @NotBlank
    private Integer estoqueMinimo;
    @NotBlank
    private LocalDate validade;
}
