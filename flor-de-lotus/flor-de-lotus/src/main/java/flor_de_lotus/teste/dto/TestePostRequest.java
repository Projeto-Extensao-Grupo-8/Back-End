package flor_de_lotus.teste.dto;

import jakarta.validation.constraints.NotBlank;
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
