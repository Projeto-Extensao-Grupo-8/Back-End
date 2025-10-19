package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimentacaoEstoqueRequest {
    @NotBlank
    @Positive
    @Schema(description = "Quantidade de unidades movidas ou usadas na consulta")
    private Integer qtd;
    @NotBlank
    @Schema(description = "Motivo ou tipo da movimentação")
    private String descricao;
    @NotBlank
    @ManyToOne
    @Schema(description = "Referência ao Teste Psicológico que foi movimentado.")
    private Integer Fkteste;

}
