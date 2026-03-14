package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class KpiFaturamentoMes {
    Double faturamento;

    public KpiFaturamentoMes(Double faturamento) {
        this.faturamento = faturamento;
    }
}
