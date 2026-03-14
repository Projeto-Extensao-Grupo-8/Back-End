package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class KpiTaxaComparecimento {
    private String percentual;

    public KpiTaxaComparecimento(String percentual) {
        this.percentual = percentual;
    }
}
