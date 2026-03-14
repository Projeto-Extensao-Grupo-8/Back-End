package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimentacaoEstoqueResponse {
    @Schema(description = "ID único do registro de movimentação, gerado pelo sistema.")
    private Integer idMovimentacaoEstoque;
    @Schema(description = "Quantidade de unidades movidas ou usadas na consulta")
    private Integer qtd;
    @Schema(description = "Data em que a movimentação ocorreu.")
    private LocalDateTime dataMovimentacao;
    @Schema(description = "Motivo ou tipo da movimentação")
    private String descricao;
    @Schema(description = "Referência ao Teste Psicológico que foi movimentado.")
    private Teste fkTeste;
    @Schema(description = "Referência a Consulta que foi movimentado os testes.")
    private Consulta fkConsulta;
}
