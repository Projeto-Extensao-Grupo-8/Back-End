package flor_de_lotus.paciente.dto;

public class ViewTotalPacientesPorAno {
    private Long qtd;

    public ViewTotalPacientesPorAno(Long totalPacientesAtivos) {
        this.qtd = totalPacientesAtivos;
    }

    public Long getQtd() {
        return qtd;
    }
}
