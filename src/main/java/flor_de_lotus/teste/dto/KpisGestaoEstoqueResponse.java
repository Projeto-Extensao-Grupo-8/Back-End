package flor_de_lotus.teste.dto;

public record KpisGestaoEstoqueResponse(Integer buscarQtdValidadeProxima90Dias,
                                        Integer buscarQtdEstoqueCritico,
                                        Integer buscarTotalUnidadesFisicas,
                                        Integer buscarTotalUnidadesDigitais) {
}
