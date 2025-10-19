package flor_de_lotus.teste;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Teste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
