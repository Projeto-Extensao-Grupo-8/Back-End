package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class GraficoComparacaoCustoReceita {

    private Double valorTeste;
    private Double valorConsulta;
    private Integer mes;

    public GraficoComparacaoCustoReceita(Double valorTeste, Double valorConsulta, Integer mes) {
        this.valorTeste = valorTeste;
        this.valorConsulta = valorConsulta;
        this.mes = mes;
    }
}
