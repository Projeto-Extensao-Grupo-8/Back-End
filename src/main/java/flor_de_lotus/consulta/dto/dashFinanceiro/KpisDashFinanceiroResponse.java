package flor_de_lotus.consulta.dto.dashFinanceiro;

import java.util.List;

public record KpisDashFinanceiroResponse(Double KpiFaturamentoMes,
                                         Double KpiFaturamentoAno,
                                         List<KpiMelhorFaturamentoAno> kpiMelhorFaturamentoAno ){
}
