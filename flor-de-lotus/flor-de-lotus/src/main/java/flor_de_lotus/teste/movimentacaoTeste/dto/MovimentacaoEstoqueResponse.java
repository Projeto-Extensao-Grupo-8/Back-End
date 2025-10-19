package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovimentacaoEstoqueResponse {
    @Schema(description = "ID único do registro de movimentação, gerado pelo sistema.")
    private Integer idMovimentacaoEstoque;;
    @Schema(description = "Quantidade de unidades movidas ou usadas na consulta")
    private Integer qtd;
    @Schema(description = "Data em que a movimentação ocorreu.")
    private LocalDate dataMovimentacao;
    @Schema(description = "Motivo ou tipo da movimentação")
    private String descricao;
    @Schema(description = "Referência ao Teste Psicológico que foi movimentado.")
    private Teste fkTeste;

}
