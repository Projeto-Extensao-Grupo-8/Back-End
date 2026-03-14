package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class KpiAgendamentoSemana {
    private Long qtd;

    public KpiAgendamentoSemana(Long qtd) {
        this.qtd = qtd;
    }
}
