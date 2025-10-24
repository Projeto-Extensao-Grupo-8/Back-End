package flor_de_lotus.consulta;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.paciente.Paciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsulta;
    private LocalDate dataConsulta;
    private Double valorConsulta;
    private String especialidade;
    @ManyToOne
    private Funcionario fkFuncionario;
    @ManyToOne
    private Paciente fkPaciente;

}
