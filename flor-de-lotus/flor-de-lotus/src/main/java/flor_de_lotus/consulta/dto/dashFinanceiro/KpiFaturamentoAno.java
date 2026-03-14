package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class KpiFaturamentoAno {
    private Double faturamento;

    public KpiFaturamentoAno(Double faturamento) {
        this.faturamento = faturamento;
    }
}
