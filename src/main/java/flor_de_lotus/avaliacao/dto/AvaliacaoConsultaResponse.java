package flor_de_lotus.avaliacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoConsultaResponse {
    private Integer id;
    private String nomePaciente;
    private LocalDate data;
    private String descricao;
}
