package flor_de_lotus.avaliacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoConsultaResponse {
    private String nomePaciente;
    private LocalDateTime data;
    private String descricao;
}
