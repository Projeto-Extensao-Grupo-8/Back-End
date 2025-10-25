package flor_de_lotus.artigo.dto;

import flor_de_lotus.artigo.Artigo;

import java.util.ArrayList;
import java.util.List;

public class ArtigoMapper {

    public static Artigo toEntity(ArtigoPostRequest dto) {
        if (dto == null) return null;

        Artigo artigo = new Artigo();
        artigo.setTitulo(dto.getTitulo());
        artigo.setDescricao(dto.getDescricao());
        artigo.setDtPublicacao(dto.getDtPublicacao());

        return artigo;
    }

    public static ArtigoResponse toResponse(Artigo artigo) {
        if (artigo == null) return null;

        ArtigoResponse resp = new ArtigoResponse();
        resp.setIdArtigo(artigo.getIdArtigo());
        resp.setTitulo(artigo.getTitulo());
        resp.setDescricao(artigo.getDescricao());
        resp.setDtPublicacao(artigo.getDtPublicacao());


        if (artigo.getFkFuncionario() != null) {

            resp.setIdFuncionario(artigo.getFkFuncionario().getIdFuncionario());
        }

        return resp;
    }

    public static List<ArtigoResponse> toResponseList(List<Artigo> lista) {
        List<ArtigoResponse> out = new ArrayList<>();
        if (lista == null) return out;
        for (Artigo a : lista) {
            out.add(toResponse(a));
        }
        return out;
    }
}