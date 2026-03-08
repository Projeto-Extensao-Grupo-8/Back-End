package flor_de_lotus.paciente.dto;

public class ViewTotalPacientesPorAno {
    private Integer qtd;

    public ViewTotalPacientesPorAno(Integer totalPacientesAtivos) {
        this.qtd = totalPacientesAtivos;
    }

    public Integer getQtd() {
        return qtd;
    }
}
