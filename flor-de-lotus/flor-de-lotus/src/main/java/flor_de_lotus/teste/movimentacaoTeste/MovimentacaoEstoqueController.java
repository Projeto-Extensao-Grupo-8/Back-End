package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.movimentacaoTeste.dto.MoviEstoqueResponseGet;
import flor_de_lotus.teste.movimentacaoTeste.dto.MoviEstoqueResponseGetFunc;
import flor_de_lotus.teste.movimentacaoTeste.dto.MovimentacaoEstoqueRequest;
import flor_de_lotus.teste.movimentacaoTeste.dto.MovimentacaoEstoqueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
@Tag(name = "Movimentação Estoque", description = "Endpoints utilizados para gerenciar a movimentação Estoque de Testes")
public class MovimentacaoEstoqueController {
    public final MovimentacaoEstoqueService service;

    @Operation(summary = "Cadastrar novas movimentações estoques de testes no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação Estoque Registrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            })
    })
    @PostMapping
    public ResponseEntity<MovimentacaoEstoqueResponse> cadastrar(@RequestBody @Valid MovimentacaoEstoqueRequest body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @Operation(summary = "Buscar todos as movimentações de testes cadastrado no Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoqueResponse>> listar(){
        List<MovimentacaoEstoqueResponse> todos = service.listar();

        if (todos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(todos);
    }
    @Operation(summary = "Buscar todos os testes cadastrado no Sistema de seu respectivo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping("/{idPaciente}")
    public ResponseEntity<List<MoviEstoqueResponseGet>> listarPorPaciente(@PathVariable Integer idPaciente){
        List<MoviEstoqueResponseGet> listaTeste = service.listarPorPaciente(idPaciente);
        if (listaTeste.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaTeste);
    }
    @Operation(summary = "Buscar todos os testes cadastrado no Sistema de seu respectivo funcionario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping("buscarPorFunc/{idFuncionario}")
    public ResponseEntity<List<MoviEstoqueResponseGetFunc>> listarPorFuncionario(@PathVariable Integer idFuncionario){
        List<MoviEstoqueResponseGetFunc> listaTeste = service.listarPorFuncionario(idFuncionario);
        if (listaTeste.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaTeste);
    }

    @Operation(summary = "Atualizar Movimentação estoque no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque Atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            })
    })
    @PatchMapping("/{idMovi}")
    public ResponseEntity<MovimentacaoEstoqueResponse> atualizarMovimentacao(@PathVariable Integer idMovi,@RequestBody @Valid MovimentacaoEstoqueRequest body){
        return ResponseEntity.status(200).body(service.atualizarParcial(idMovi,body));
    }

}
