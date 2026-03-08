package flor_de_lotus.paciente.dto;

public class ViewTop5paciente {
    private String nomePaciente;
    private Integer consultas;
    private Double valor;
    private Boolean ativo;

    public ViewTop5paciente(String nomePaciente, Integer consultas, Double valor, Boolean ativo) {
        this.nomePaciente = nomePaciente;
        this.consultas = consultas;
        this.valor = valor;
        this.ativo = ativo;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public Integer getConsultas() {
        return consultas;
    }

    public Double getValor() {
        return valor;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
