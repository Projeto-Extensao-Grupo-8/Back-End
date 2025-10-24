package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.ConsultaMapper;
import flor_de_lotus.consulta.dto.ConsultaPostRequestBody;
import flor_de_lotus.consulta.dto.ConsultaResponseBody;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.exception.BadRequestException;
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
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Endpoints utilizados para gerenciar as Consultas")
public class ConsultaController {

    private final ConsultaService service;

    @Operation(summary = "Cadastrar uma nova consulta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConsultaResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou inconsistentes",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class)))
    })
    @PostMapping
    public ResponseEntity<ConsultaResponseBody> cadastrar(@RequestBody @Valid ConsultaPostRequestBody body) {
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @Operation(summary = "Listar todas as consultas cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não há consultas cadastradas")
    })
    @GetMapping
    public ResponseEntity<List<ConsultaResponseBody>> listarTodas() {
        List<ConsultaResponseBody> lista = service.listarTodas();
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar consulta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseBody> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ConsultaMapper.of(service.buscarPorIdOuThrow(id)));
    }

    @Operation(summary = "Deletar consulta via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Atualizar parcialmente uma consulta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConsultaResponseBody.class))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ConsultaResponseBody> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody @Valid ConsultaPostRequestBody body) {
        return ResponseEntity.status(200).body(service.atualizarParcial(id, body));
    }

    @Operation(summary = "Listar consultas de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Paciente não possui consultas cadastradas")
    })
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<ConsultaResponseBody> lista = service.listarPorPaciente(idPaciente);
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Listar consultas de um funcionário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Funcionário não possui consultas cadastradas")
    })
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<ConsultaResponseBody> lista = service.listarPorFuncionario(idFuncionario);
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }


}
