package flor_de_lotus.paciente.dto.dashPaciente;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GraficoTaxaRetencaoMes {
    private String mes;
    private BigDecimal percentualRetencao;

    public GraficoTaxaRetencaoMes(String mes, BigDecimal percentualRetencao) {
        this.mes = mes;
        this.percentualRetencao = percentualRetencao;
    }
}
