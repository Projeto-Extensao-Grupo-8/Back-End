package flor_de_lotus.Controller;

import flor_de_lotus.Domain.Usuario;
import flor_de_lotus.Repository.UsuarioRepository;
import flor_de_lotus.Request.UsuarioLoginRequestBody;
import flor_de_lotus.Request.UsuarioPostRequestBody;
import flor_de_lotus.Request.UsuarioReplaceRequestBody;
import flor_de_lotus.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioPostRequestBody body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody @Valid UsuarioLoginRequestBody body){
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
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }








}
