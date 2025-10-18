package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.teste.Teste;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimentacaoEstoqueRequest {
    @NotBlank
    private String tipoMovimentacao;
    @NotBlank
    private Integer qtd;
    @NotBlank
    private String descricao;
    @ManyToOne
    @NotBlank
    private Integer fkTeste;
    @ManyToOne
    private Integer fkConsulta;
}
