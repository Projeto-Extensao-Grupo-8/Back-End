package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.teste.Teste;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MovimentacaoEstoqueRequest {
    @Positive
    @Schema(description = "Quantidade de unidades movidas ou usadas na consulta", example = "55")
    private Integer qtd;
    @Schema(description = "Descrição da movimentação", example = "Usada para avaliar novamente o paciente")
    private String descricao;
    @ManyToOne
    @Schema(description = "Referência ao Teste Psicológico que foi movimentado.", example = "1")
    private Integer fkTeste;
    @Schema(description = "Referência a Consulta que foi movimentado os testes.", example = "1")
    private Integer fkConsulta;

}
