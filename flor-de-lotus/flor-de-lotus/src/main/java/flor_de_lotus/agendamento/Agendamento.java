package flor_de_lotus.agendamento;

import flor_de_lotus.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "agenda")
@NoArgsConstructor
@Getter
@Setter
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;

    private LocalTime inicioTempo;

    private LocalTime finalTempo;

    private LocalDate dataDia;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario", nullable = false)
    private Funcionario fkFuncionario;
}
