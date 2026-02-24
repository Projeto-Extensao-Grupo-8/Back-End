package flor_de_lotus.usuario.dto;

import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.usuario.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioPostRequestBody dto, Endereco endereco) {

        Usuario entinty = new Usuario();

        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setDataNascimento(dto.getDataNascimento());
        entinty.setCpf(dto.getCpf());
        entinty.setTelefone(dto.getTelefone());
        entinty.setSenha(dto.getSenha());
        entinty.setFkEndereco(endereco);
        entinty.setNewsletter(dto.getNewsletter());

        return entinty;

    }

    public static Usuario toEntity(UsuarioPostRequestBody dto) {

        Usuario entinty = new Usuario();

        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setCpf(dto.getCpf());
        entinty.setDataNascimento(dto.getDataNascimento());
        entinty.setTelefone(dto.getTelefone());
        entinty.setSenha(dto.getSenha());
        entinty.setNewsletter(dto.getNewsletter());

        return entinty;

    }

    public static Usuario toEntity(UsuarioReplaceRequestBody dto) {

        Usuario entinty = new Usuario();

        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setCpf(dto.getCpf());
        entinty.setTelefone(dto.getTelefone());
        entinty.setSenha(dto.getSenha());
        entinty.setFkEndereco(dto.getFkEndereco());
        entinty.setNewsletter(dto.getNewsletter());

        return entinty;

    }

    public static Usuario toEntity(UsuarioLoginRequestBody dto) {

        Usuario entinty = new Usuario();

        entinty.setEmail(dto.getEmail());
        entinty.setSenha(dto.getSenha());

        return entinty;

    }

    public static UsuarioResponseBody toResponse(Usuario usuario) {

        UsuarioResponseBody usuarioResponseBody = new UsuarioResponseBody();

        usuarioResponseBody.setId(usuario.getIdUsuario());
        usuarioResponseBody.setNome(usuario.getNome());
        usuarioResponseBody.setEmail(usuario.getEmail());
        usuarioResponseBody.setNivelPermissao(usuario.getNivelPermissao());

        return usuarioResponseBody;

    }

    public static UsuarioTokenResponseBody toTokenResponse(Usuario usuario, String token) {
        UsuarioTokenResponseBody usuarioTokenResponseBody = new UsuarioTokenResponseBody();

        usuarioTokenResponseBody.setUserId(usuario.getIdUsuario());
        usuarioTokenResponseBody.setEmail(usuario.getEmail());
        usuarioTokenResponseBody.setNome(usuario.getNome());
        usuarioTokenResponseBody.setNivelPermissao(usuario.getNivelPermissao());
        usuarioTokenResponseBody.setToken(token);

        return usuarioTokenResponseBody;

    }

}
