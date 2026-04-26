package flor_de_lotus.consulta.dto.dashAgendamento;

public record GraficoDesempenhoSemanalResponse(
        String dia,
        Integer confirmadas,
        Integer realizadas,
        Integer canceladas,
        Integer pendentes
) {}