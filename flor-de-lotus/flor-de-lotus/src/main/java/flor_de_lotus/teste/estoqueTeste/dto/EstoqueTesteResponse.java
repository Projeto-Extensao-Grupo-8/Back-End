package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EstoqueTesteResponse {
    @Schema(description = "ID do registro de estoque, gerado pelo banco de dados")
    private Integer idEstoqueTeste;
    @Schema(description = "Quantidade atual de unidades do Teste em estoque.")
    private Integer qtdAtual;
    @Schema(description = "Data em que o registro de estoque foi criado ou atualizado")
    private LocalDate dtReferencia;
    @Schema(description = "Referência ao Teste Psicológico ao qual este registro de estoque se aplica.")
    private Teste fkTeste;
}
