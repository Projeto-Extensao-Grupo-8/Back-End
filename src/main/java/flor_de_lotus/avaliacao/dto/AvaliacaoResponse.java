package flor_de_lotus.avaliacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponse {
    private String descricao;
    private Integer estrelas;
    private LocalDate dataAvaliacao;
    private Integer idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;
    private Integer idConsulta;
    private LocalDateTime dataConsulta;
}
