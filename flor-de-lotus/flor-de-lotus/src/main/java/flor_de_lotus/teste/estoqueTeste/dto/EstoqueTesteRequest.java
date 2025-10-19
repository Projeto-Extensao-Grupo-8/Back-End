package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class EstoqueTesteRequest {
    @NotNull
    @Min(value = 0, message = "A quantidade atual não pode ser negativa.")
    @Schema(
            description = "Quantidade atual de unidades do Teste em estoque.",
            example = "55"
    )
    private Integer qtdAtual;
    @NotNull
    @Schema(description = "Referência ao Teste Psicológico ao qual este registro de estoque se aplica.", example = "1")
    private Integer fkTeste;
}
