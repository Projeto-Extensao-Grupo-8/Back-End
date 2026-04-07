package flor_de_lotus.paciente.dto.dashPaciente;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class KpiTaxaRetencao {
    private BigDecimal taxaPercentual;

    public KpiTaxaRetencao(BigDecimal taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }
}
