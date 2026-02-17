package flor_de_lotus.usuario.dto;

import flor_de_lotus.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UsuarioDetalhes implements UserDetails {


    private final String nome;
    private final String email;
    private final String senha;
    private final String nivelAcesso;

    public UsuarioDetalhes(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.nivelAcesso = usuario.getNivelPermissao();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("4".equals(nivelAcesso)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("3".equals(nivelAcesso)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PACIENTE"));
        } else if ("2".equals(nivelAcesso)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USUARIO"));
        }

        return authorities;
    };

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
