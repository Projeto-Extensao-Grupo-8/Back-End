package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.estoqueTeste.EstoqueTeste;
import flor_de_lotus.usuario.Usuario;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimentacaoEstoque;
    private Integer qtd;
    private LocalDate dataMovimentacao;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "idTeste")
    private Teste teste;


}
