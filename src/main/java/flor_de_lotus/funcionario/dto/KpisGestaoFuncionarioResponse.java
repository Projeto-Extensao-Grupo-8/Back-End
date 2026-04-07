package flor_de_lotus.funcionario.dto;

public record KpisGestaoFuncionarioResponse(long totaisFuncionariosAtivos,
                                            long totaisFuncionarios,
                                            long totalFuncionariosDesativados,
                                            long totalEspecialidades) {
}
