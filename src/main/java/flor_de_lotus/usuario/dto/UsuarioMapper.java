package flor_de_lotus.usuario.dto;

import flor_de_lotus.endereco.Endereco;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import flor_de_lotus.usuario.Usuario;

import java.util.List;

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
        entinty.setNivelPermissao(dto.getNivelPermissao());
        entinty.setDataNascimento(dto.getDataNascimento());
        return entinty;
    }

    public static Usuario toEntityUsuario(FuncionarioPostRequestBody dto) {
        Usuario entinty = new Usuario();
        System.out.println(dto.getEmail());
        System.out.println(dto.getSenha());
        System.out.println(dto.getCpf());

        Endereco endereco = new Endereco();
        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setDataNascimento(dto.getDataNascimento());
        entinty.setCpf(dto.getCpf());
        entinty.setTelefone(dto.getTelefone());
        entinty.setSenha(dto.getSenha());
        endereco.setCep(dto.getCep());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        entinty.setFkEndereco(endereco);
        entinty.setDataNascimento(dto.getDataNascimento());
        return entinty;
    }

    public static Usuario toEntity(UsuarioReplaceRequestBody dto) {
        Usuario entinty = new Usuario();

        entinty.setNome(dto.getNome());
        entinty.setEmail(dto.getEmail());
        entinty.setDataNascimento(dto.getDataNascimento());
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
        usuarioResponseBody.setTelefone(usuario.getTelefone());
        usuarioResponseBody.setDataNascimento(usuario.getDataNascimento());
        usuarioResponseBody.setDataCadastro(usuario.getDataCadastro());
        usuarioResponseBody.setEndereco(usuario.getFkEndereco());

        return usuarioResponseBody;
    }

    public static UsuarioTokenResponseBody toTokenResponse(Usuario usuario, String token) {
        UsuarioTokenResponseBody usuarioTokenResponseBody = new UsuarioTokenResponseBody();

        usuarioTokenResponseBody.setIdUsuario(usuario.getIdUsuario());
        usuarioTokenResponseBody.setEmail(usuario.getEmail());
        usuarioTokenResponseBody.setNome(usuario.getNome());
        usuarioTokenResponseBody.setNivelPermissao(usuario.getNivelPermissao());
        usuarioTokenResponseBody.setToken(token);

        return usuarioTokenResponseBody;
    }

    public static UsuarioTokenResponseBody toTokenResponse(Usuario usuario, String token, Integer idPaciente, Integer idFuncionario) {
        UsuarioTokenResponseBody usuarioTokenResponseBody = new UsuarioTokenResponseBody();

        usuarioTokenResponseBody.setIdUsuario(usuario.getIdUsuario());
        usuarioTokenResponseBody.setEmail(usuario.getEmail());
        usuarioTokenResponseBody.setNome(usuario.getNome());
        usuarioTokenResponseBody.setNivelPermissao(usuario.getNivelPermissao());
        usuarioTokenResponseBody.setToken(token);
        usuarioTokenResponseBody.setIdPaciente(idPaciente);
        usuarioTokenResponseBody.setIdFuncionario(idFuncionario);

        return usuarioTokenResponseBody;
    }

    public static List<UsuarioResponseBody> toResponseList(List<Usuario> usuarios) {
        if (usuarios == null) {
            return null;
        }
        return usuarios.stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

}
