package flor_de_lotus.consulta.dto;

import lombok.Getter;

@Getter
public class KardResumoFinanceiro {
    private Double faturamentoAtual;
    private Double faturamentoAnterior;
    private Double crescimentoPercentual;

    public KardResumoFinanceiro(Double faturamentoAtual, Double faturamentoAnterior, Double crescimentoPercentual) {
        this.faturamentoAtual = faturamentoAtual;
        this.faturamentoAnterior = faturamentoAnterior;
        this.crescimentoPercentual = crescimentoPercentual;
    }
}
