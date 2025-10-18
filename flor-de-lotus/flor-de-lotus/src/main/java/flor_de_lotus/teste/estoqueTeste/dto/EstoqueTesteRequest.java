package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class EstoqueTesteRequest {
    @NotNull
    private Integer qtdAtual;
    @NotNull
    private Integer fkTeste;
}
