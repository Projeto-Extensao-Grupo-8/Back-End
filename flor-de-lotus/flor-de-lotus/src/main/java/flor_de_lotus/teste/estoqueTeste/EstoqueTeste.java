package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.teste.Teste;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EstoqueTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstoqueTeste;
    private Integer qtdAtual;
    private LocalDate dtReferencia;
    @ManyToOne
    @JoinColumn(name = "idTeste", nullable = false)
    private Teste fkTeste;

}
