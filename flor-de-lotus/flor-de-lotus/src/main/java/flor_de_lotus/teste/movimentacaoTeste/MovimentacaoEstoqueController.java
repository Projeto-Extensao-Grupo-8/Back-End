package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.movimentacaoTeste.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<MovimentacaoEstoqueResponse> cadastrar(@RequestBody @Valid MovimentacaoEstoqueRequest body){

        MovimentacaoEstoque estoqueSalvar = MovimentacaoEstoqueMapper.toEntity(body);

        MovimentacaoEstoqueResponse moviEstoqueResponse = MovimentacaoEstoqueMapper.toResponse(service.cadastrar(estoqueSalvar, body.getFkTeste(), body.getFkConsulta()));

        return ResponseEntity.status(201).body(moviEstoqueResponse);

    }

    @Operation(summary = "Buscar todos as movimentações de testes cadastrado no Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimentacaoEstoqueResponse>> listar(){
        List<MovimentacaoEstoque> todos = service.listar();

        if (todos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<MovimentacaoEstoqueResponse> moviEstoqueResponse = MovimentacaoEstoqueMapper.toResponseList(todos);

        return ResponseEntity.status(200).body(moviEstoqueResponse);

    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema de seu respectivo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping("/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<MoviEstoqueResponseGet>> listarPorPaciente(@PathVariable Integer idPaciente){

        List<MoviEstoqueResponseGet> listaResponseGet = MovimentacaoEstoqueMapper.toResponseListGet(service.listarPorPaciente(idPaciente));

        if (listaResponseGet.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaResponseGet);

    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema de seu respectivo funcionario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping("buscarPorFunc/{idFuncionario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<MoviEstoqueResponseGetFunc>> listarPorFuncionario(@PathVariable Integer idFuncionario){

        List<MoviEstoqueResponseGetFunc> listaResponseGet = MovimentacaoEstoqueMapper.toResponseListGetFunc(service.listarPorFuncionario(idFuncionario));

        if (listaResponseGet.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaResponseGet);

    }

    @Operation(summary = "Atualizar Movimentação estoque no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque Atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            })
    })
    @PatchMapping("/{idMovi}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<MovimentacaoEstoqueResponse> atualizarMovimentacao(@PathVariable Integer idMovi,@RequestBody @Valid MovimentacaoEstoqueRequest body){

        MovimentacaoEstoque entity = MovimentacaoEstoqueMapper.toEntity(body);

        MovimentacaoEstoqueResponse response = MovimentacaoEstoqueMapper.toResponse(service.atualizarParcial(idMovi, entity, body.getFkConsulta(), body.getFkTeste()));

        return ResponseEntity.status(200).body(response);
    }

}
