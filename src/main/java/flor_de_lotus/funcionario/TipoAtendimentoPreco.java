package flor_de_lotus.funcionario;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoAtendimentoPreco {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atendimento")
    private TipoAtendimento tipoAtendimento;

    @Column(name = "preco")
    private Double preco;
}