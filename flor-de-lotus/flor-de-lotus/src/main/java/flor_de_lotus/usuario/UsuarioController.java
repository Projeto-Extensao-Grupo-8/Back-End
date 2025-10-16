package flor_de_lotus.usuario;

import flor_de_lotus.usuario.dto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/cadastro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioResponseBody> cadastrar(@RequestBody @Valid UsuarioPostRequestBody body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenResponseBody> login(@RequestBody @Valid UsuarioLoginRequestBody body){
        return ResponseEntity.status(200).body(service.login(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletePorId(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarParcial(@PathVariable Integer id,@RequestBody @Valid UsuarioReplaceRequestBody body){
        return ResponseEntity.status(200).body(service.atulizarParcial(id,body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.buscarPorIdOuThrow(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseBody>> listarTodos(){

        List<UsuarioResponseBody> lista = service.listarTodos();
        return ResponseEntity.status(200).body(lista);

    }

}
