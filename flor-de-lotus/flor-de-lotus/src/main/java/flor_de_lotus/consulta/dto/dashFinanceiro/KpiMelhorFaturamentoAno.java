package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class KpiMelhorFaturamentoAno {
    private Integer mes;
    private Double totalMes;

    public KpiMelhorFaturamentoAno(Integer mes, Double totalMes) {
        this.mes = mes;
        this.totalMes = totalMes;
    }
}
