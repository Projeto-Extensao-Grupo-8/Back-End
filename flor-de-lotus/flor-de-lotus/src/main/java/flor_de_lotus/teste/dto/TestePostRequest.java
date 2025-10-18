package flor_de_lotus.teste.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TestePostRequest {
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
    @NotNull
    private Double preco;
    @NotNull
    private Integer estoqueMinimo;
    @NotNull
    private LocalDate validade;

}
