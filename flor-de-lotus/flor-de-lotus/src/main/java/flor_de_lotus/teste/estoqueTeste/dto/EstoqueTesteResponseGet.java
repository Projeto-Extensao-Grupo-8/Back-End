package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EstoqueTesteResponseGet {
    private Integer idEstoqueTeste;
    private Integer qtdAtual;
    private LocalDate dtReferencia;
    private Teste fkTeste;
}
