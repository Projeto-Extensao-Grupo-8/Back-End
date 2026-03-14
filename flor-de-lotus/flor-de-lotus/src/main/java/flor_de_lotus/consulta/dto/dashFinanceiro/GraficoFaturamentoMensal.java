package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class GraficoFaturamentoMensal {
    private Integer mes;
    private Double valor;

    public GraficoFaturamentoMensal(Integer mes, Double valor) {
        this.mes = mes;
        this.valor = valor;
    }
}
