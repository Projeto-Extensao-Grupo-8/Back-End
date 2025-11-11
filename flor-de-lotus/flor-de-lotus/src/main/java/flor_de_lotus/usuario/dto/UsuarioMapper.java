package flor_de_lotus.usuario.dto;

import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.usuario.Usuario;

public class UsuarioMapper {

    public static Usuario of(UsuarioPostRequestBody dto, Endereco endereco) {

        Usuario entinty = new Usuario();

        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setCpf(dto.getCpf());
        entinty.setTelefone(dto.getTelefone());
        entinty.setSenha(dto.getSenha());
        entinty.setFkEndereco(endereco);
        entinty.setNewsletter(dto.getNewsletter());

        return entinty;

    }

    public static UsuarioResponseBody of(Usuario usuario) {

        UsuarioResponseBody usuarioResponseBody = new UsuarioResponseBody();

        usuarioResponseBody.setId(usuario.getIdUsuario());
        usuarioResponseBody.setNome(usuario.getNome());
        usuarioResponseBody.setEmail(usuario.getEmail());
        usuarioResponseBody.setNivelPermissao(usuario.getNivelPermissao());

        return usuarioResponseBody;

    }

    public static UsuarioTokenResponseBody of(Usuario usuario, String token) {
        UsuarioTokenResponseBody usuarioTokenResponseBody = new UsuarioTokenResponseBody();

        usuarioTokenResponseBody.setUserId(usuario.getIdUsuario());
        usuarioTokenResponseBody.setEmail(usuario.getEmail());
        usuarioTokenResponseBody.setNome(usuario.getNome());
        usuarioTokenResponseBody.setNivelPermissao(usuario.getNivelPermissao());
        usuarioTokenResponseBody.setToken(token);

        return usuarioTokenResponseBody;

    }

}
