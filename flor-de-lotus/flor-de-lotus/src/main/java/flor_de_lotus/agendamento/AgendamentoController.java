package flor_de_lotus.agendamento;

import flor_de_lotus.agendamento.dto.*;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints de gerenciamento de agendamentos de funcionários")
public class AgendamentoController {

    private final AgendamentoService service;

    @Operation(summary = "Publicar um novo agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Horário indisponível")
    })
    @PostMapping
    public ResponseEntity<AgendamentoResponse> publicar(@RequestBody @Valid AgendamentoPostRequest dto) {
        return ResponseEntity.status(201).body(service.publicar(dto));
    }

    @Operation(summary = "Remover um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        service.remover(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar todos os agendamentos")
    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos() {
        List<AgendamentoResponse> lista = service.listarTodos();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }
    @Operation(summary = "Listar agendamentos de um funcionário")
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<AgendamentoResponse> lista = service.listarPorFuncionario(idFuncionario);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }
}
