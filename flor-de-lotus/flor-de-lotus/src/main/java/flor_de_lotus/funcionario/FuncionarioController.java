package flor_de_lotus.funcionario;

import flor_de_lotus.funcionario.dto.FuncionarioPatchRequestBody;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    public final FuncionarioService service;

    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@RequestBody @Valid FuncionarioPostRequestBody body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos(){
        List<Funcionario> listaTodos = service.listarTodos();
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.buscarPorIdOuThrow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarFuncionario(@PathVariable Integer id){
        service.desativarProfissional(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarFuncionario(@PathVariable Integer id){
        service.ativarProfissional(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarParcial(@PathVariable Integer id, @RequestBody FuncionarioPatchRequestBody body ){
        return ResponseEntity.status(200).body(service.atualizarParcial(id,body));
    }




}
