package flor_de_lotus.avaliacao;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAvaliacao;

    private String descricao;
    private Integer estrelas;
    private LocalDateTime dataAvaliacao;


    @OneToOne
    @JoinColumn(name = "fk_consulta", nullable = false)
    private Consulta fkConsulta;

    @OneToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario fkFuncionario;
}
