package flor_de_lotus.consulta.dto;

import flor_de_lotus.consulta.StatusConsulta;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequestDTO(@NotNull(message = "O campo novoStatus é obrigatório.")
                                        StatusConsulta novoStatus) {
}
