package flor_de_lotus.teste.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TestePatchRequest {
    private String codigo;
    private String nome;
    private String categoria;
    private String subCategoria;
    private String editora;
    private String tipo;
    private Double preco;
    private Integer estoqueMinimo;
    private LocalDate validade;
    private Integer qtd;
}
