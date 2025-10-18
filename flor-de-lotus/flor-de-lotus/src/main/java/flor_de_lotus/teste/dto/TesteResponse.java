package flor_de_lotus.teste.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TesteResponse {
    private Integer idTeste;
    private String codigo;
    private String nome;
    private String categoria;
    private String subCategoria;
    private String editora;
    private String tipo;
    private Double preco;
    private Integer estoqueMinimo;
    private LocalDate validade;

}
