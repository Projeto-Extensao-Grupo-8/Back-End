package flor_de_lotus.teste.movimentacaoTeste.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor

public class UltimoTestePacienteDTO {
    private String codigoTeste;
    private String nomeTeste;
    private String categoriaTeste;
    private LocalDateTime dataAplicacao;
    private Integer quantidadeUtilizada;
    private String descricaoMovimentacao;
}
