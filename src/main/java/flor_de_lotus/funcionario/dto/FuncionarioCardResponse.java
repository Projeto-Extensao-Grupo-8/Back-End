package flor_de_lotus.funcionario.dto;

import flor_de_lotus.funcionario.Especialidade;
import flor_de_lotus.funcionario.Modalidade;
import flor_de_lotus.funcionario.TipoAtendimentoPreco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCardResponse {
    private String nomeUsuario;
    private List<Especialidade> especialidades;
    private Modalidade modalidade;
}
