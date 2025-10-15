package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.teste.estoqueTeste.EstoqueTeste;
import flor_de_lotus.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue
    private Integer idMovimentacaoEstoque;
    private String tipoMovimentacao;
    private Integer qtd;
    private LocalDate dataMovimentacao;
    private String descricao;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private EstoqueTeste estoqueTeste;

}
