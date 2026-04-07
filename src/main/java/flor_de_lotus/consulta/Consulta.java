package flor_de_lotus.consulta;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.paciente.Paciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsulta;
    private LocalDateTime data;
    private Double valor;
    private String especialidade;
    private String tipo;
    @Enumerated(EnumType.STRING)
    private StatusConsulta status;
    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario fkFuncionario;
    @ManyToOne
    @JoinColumn(name = "fk_paciente")
    private Paciente fkPaciente;


    public void confirmar() {
        if (this.status == StatusConsulta.PENDENTE) {
            this.status = StatusConsulta.CONFIRMADA;
        } else {
            throw new IllegalStateException("Apenas consultas pendentes podem ser confirmadas.");
        }
    }

    public void cancelar() {
        if (this.status != StatusConsulta.REALIZADA) {
            this.status = StatusConsulta.CANCELADA;
        } else {
            throw new IllegalStateException("Uma consulta já realizada não pode ser cancelada.");
        }
    }

    public void marcarComoRealizada() {
        if (this.status == StatusConsulta.CONFIRMADA) {
            this.status = StatusConsulta.REALIZADA;
        } else {
            throw new IllegalStateException("A consulta precisa estar confirmada para ser realizada.");
        }
    }

}
