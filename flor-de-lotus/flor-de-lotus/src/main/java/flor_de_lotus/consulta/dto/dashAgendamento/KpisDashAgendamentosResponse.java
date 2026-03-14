package flor_de_lotus.consulta.dto.dashAgendamento;

import java.util.List;

public record KpisDashAgendamentosResponse(
        Long kpiAgendamentosSemana,
        String kpiTaxaComparecimento ,
        Long kpiConsultasRealizadas,
        List<KpiCancelamentos> kpiCancelamentos
) {


}
