package flor_de_lotus.paciente;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.paciente.dto.PacienteMapper;
import flor_de_lotus.paciente.dto.PacientePostRequestBody;
import flor_de_lotus.paciente.dto.PacienteResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Endpoints utilizados para gerenciar os Pacientes")
public class PacienteController {

    private final PacienteService service;

    @Operation(summary = "Cadastrar novo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseBody> cadastrar(@RequestBody @Valid PacientePostRequestBody body) {

        Paciente pacienteCadastrar = PacienteMapper.of(body);

        Paciente pacienteCadastrado = service.cadastrar(body.getFkUsuario());

        PacienteResponseBody response = PacienteMapper.of(pacienteCadastrado);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todos os pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada"),
            @ApiResponse(responseCode = "204", description = "Não há pacientes cadastrados")
    })
    @GetMapping
    public ResponseEntity<List<PacienteResponseBody>> listarTodos() {
        List<PacienteResponseBody> lista = service.listarTodos().stream().map(PacienteMapper::of).toList();
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar paciente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseBody> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(PacienteMapper.of(service.buscarPorIdOuThrow(id)));
    }

    @Operation(summary = "Atualizar parcialmente um paciente")
    @PatchMapping("/{id}")
    public ResponseEntity<PacienteResponseBody> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody @Valid PacientePostRequestBody body) {

        Paciente atualizacao = PacienteMapper.of(body);

        PacienteResponseBody atualizado = PacienteMapper.of(service.atualizarParcial(id, atualizacao, body.getFkUsuario()));

        return ResponseEntity.status(200).body(atualizado);

    }

    @Operation(summary = "Deletar paciente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

}
