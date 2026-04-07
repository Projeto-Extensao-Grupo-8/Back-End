package flor_de_lotus.consulta;


import lombok.Getter;

@Getter
public enum EspecialidadeConsulta {
    PRIMEIRA_VEZ("Primeira consulta", 60),
    SESSAO_INDIVIDUAL("Consulta individual", 30),
    SESSAO_FAMILIAR("Consulta em familia", 45),
    RETORNO("Consulta retorno", 30),
    SESSAO_CASAL("Consulta de casal", 120);


    private final String descricao;
    private final Integer duracaoMinutos;

    EspecialidadeConsulta(String descricao, Integer duracaoMinutos) {
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
    }

}
