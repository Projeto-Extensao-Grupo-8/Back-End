package flor_de_lotus.avaliacao;

import flor_de_lotus.consulta.Consulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "idConsulta", nullable = false)
    private Consulta fkConsulta;
}
