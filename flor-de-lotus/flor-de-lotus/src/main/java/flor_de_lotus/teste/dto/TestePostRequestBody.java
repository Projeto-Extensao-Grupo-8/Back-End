package flor_de_lotus.teste.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TestePostRequestBody {
    private String codigo;
    private String categoria;
    private String subCategoria;
    private String editora;
    private String tipo;
    private Double preco;
    private String link;
    private Integer estoqueMinimo;
    private Integer estoqueAtual;
    private LocalDate validade;
    private Integer fkClinica;
}
