package flor_de_lotus.teste.dto;

import flor_de_lotus.teste.Teste;

import java.util.ArrayList;
import java.util.List;

public class TesteMapper {
    public static Teste toEntity(TestePostRequest dto){

        if (dto == null){
            return null;
        }

        Teste teste = new Teste();
        teste.setCategoria(dto.getCategoria());
        teste.setCodigo(dto.getCodigo());
        teste.setEditora(dto.getEditora());
        teste.setTipo(dto.getTipo());
        teste.setPreco(dto.getPreco());
        teste.setEstoqueMinimo(dto.getEstoqueMinimo());
        teste.setValidade(dto.getValidade());

        return teste;

    }

    public static Teste toEntity(TesteResponse dto){

        if (dto == null){
            return null;
        }

        Teste teste = new Teste();
        teste.setCategoria(dto.getCategoria());
        teste.setCodigo(dto.getCodigo());
        teste.setEditora(dto.getEditora());
        teste.setTipo(dto.getTipo());
        teste.setPreco(dto.getPreco());
        teste.setEstoqueMinimo(dto.getEstoqueMinimo());
        teste.setValidade(dto.getValidade());

        return teste;

    }

    public static TesteResponse toResponse(Teste teste) {

        if (teste == null) {
            return null;
        }

        TesteResponse testeResposta = new  TesteResponse();
        testeResposta.setIdTeste(teste.getIdTeste());
        testeResposta.setCategoria(teste.getCategoria());
        testeResposta.setCodigo(teste.getCodigo());
        testeResposta.setEditora(teste.getEditora());
        testeResposta.setTipo(teste.getTipo());
        testeResposta.setPreco(teste.getPreco());
        testeResposta.setEstoqueMinimo(teste.getEstoqueMinimo());
        testeResposta.setValidade(teste.getValidade());

        return testeResposta;
    }

    public static List<TesteResponse> toResponseList(List<Teste> lista){
        List<TesteResponse> todosConsulta = new ArrayList<>();

        for (Teste t: lista){
            TesteResponse testeResponse = TesteMapper.toResponse(t);
            todosConsulta.add(testeResponse);
        }

        return todosConsulta;

    }
}
