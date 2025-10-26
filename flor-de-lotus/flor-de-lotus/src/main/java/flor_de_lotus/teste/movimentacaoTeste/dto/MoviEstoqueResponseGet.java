package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.consulta.Consulta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MoviEstoqueResponseGet {
    @Schema(description = "ID único do registro de movimentação, gerado pelo sistema.")
    private Integer idMovimentacaoEstoque;;
    private Integer qtd;
    @Schema(description = "Data em que a movimentação ocorreu.")
    private LocalDate dataMovimentacao;
    @Schema(description = "Motivo ou tipo da movimentação")
    private String descricao;
    @Schema(description = "Referência ao Teste Psicológico que foi movimentado.")
    private String nomeTeste;
    @Schema(description = "Referência a Consulta que foi movimentado os testes.")
    private Consulta fkConsulta;

    public Boolean getIsValido() {
        return LocalDate.now().isAfter(
                LocalDate.of(dataMovimentacao.getYear()+1, dataMovimentacao.getMonth(), dataMovimentacao.getDayOfMonth()));
    }
}
