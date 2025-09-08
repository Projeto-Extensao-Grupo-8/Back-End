package flor_de_lotus.Service;


import flor_de_lotus.Domain.Usuario;
import flor_de_lotus.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ServiceUsuario {
    public final UsuarioRepository repository;

    public ServiceUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Usuario cadastrarUsuario(Usuario usuario){
        if (repository.existsByEmailandCPF(usuario.getEmail(), usuario.getCpf())){
            return new R
        }
        return repository.save(usuario);
    }


}
