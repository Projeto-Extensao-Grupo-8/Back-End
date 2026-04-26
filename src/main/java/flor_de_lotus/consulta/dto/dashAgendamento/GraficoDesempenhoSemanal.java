package flor_de_lotus.consulta.dto.dashAgendamento;

public interface GraficoDesempenhoSemanal {

    Integer getDia();
    Integer getConfirmadas();
    Integer getRealizadas();
    Integer getCanceladas();
    Integer getPendentes();

}