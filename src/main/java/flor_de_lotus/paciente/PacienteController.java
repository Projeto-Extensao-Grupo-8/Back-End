package flor_de_lotus.paciente;

import flor_de_lotus.funcionario.dto.FuncionarioResponse;
import flor_de_lotus.funcionario.mapper.FuncionarioMapper;
import flor_de_lotus.paciente.dto.*;
import flor_de_lotus.paciente.dto.dashPaciente.GraficoNovosPacientesPorMes;
import flor_de_lotus.paciente.dto.dashPaciente.GraficoTaxaRetencaoMes;
import flor_de_lotus.paciente.dto.dashPaciente.ViewTop5paciente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    public ResponseEntity<PacienteResponseBody> cadastrar(@RequestBody @Valid PacienteCadastroRequest body) {

        Paciente pacienteCadastrado = service.cadastrar(body.getFkUsuario());

        PacienteResponseBody response = PacienteMapper.toResponse(pacienteCadastrado);

        return ResponseEntity.status(201).body(response);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada"),
            @ApiResponse(responseCode = "204", description = "Não há pacientes cadastrados")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PacienteResponseBody>> listarTodos() {
        List<PacienteResponseBody> lista = PacienteMapper.toResponseList(service.listarTodos());
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar paciente por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE','FUNCIONARIO' ,'ADMIN')")
    public ResponseEntity<PacienteResponseBody> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(PacienteMapper.toResponse(service.buscarPorIdOuThrow(id)));
    }

    @Operation(summary = "Atualizar parcialmente um paciente")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    public ResponseEntity<PacienteResponseBody> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody @Valid PacientePostRequestBody body) {

        Paciente atualizado = service.atualizarParcial(id, body);

        return ResponseEntity.status(200).body(PacienteMapper.toResponse(atualizado));
    }

    @Operation(summary = "Deletar paciente por ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Listar pacientes por funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes do funcionário retornada"),
            @ApiResponse(responseCode = "204", description = "O funcionário não possui pacientes")
    })
    @GetMapping("/funcionario/{idFuncionario}")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<List<PacienteResponseBody>> listarPacientesPorFuncionario(@PathVariable Integer idFuncionario) {
        List<PacienteResponseBody> lista = PacienteMapper.toResponseList(service.listarPacientesPorFuncionario(idFuncionario));
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar quantidade de pacientes ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "quantidade de pacientes ativos"),
    })
    @GetMapping("/kpiPacientesAtivos")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<Long> totalPacientes() {
        Long totalPacientes = service.totalPacientes();
        return ResponseEntity.status(200).body(totalPacientes);
    }

    @Operation(summary = "Buscar quantidade de pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "quantidade de pacientes ativos por ano")
    })
    @GetMapping("/kpiPacientesAno/ano/{ano}")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<Long> totalPacientes(@PathVariable @NotNull @Valid Integer ano) {
        Long totalPacientes = service.totalPacientesPorAno(ano);
        return ResponseEntity.status(200).body(totalPacientes);
    }

    @Operation(summary = "Buscar top 5 pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "quantidade de pacientes ativos"),
            @ApiResponse(responseCode = "204", description = "não possui pacientes")
    })
    @GetMapping("/top5Pacientes")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<List<ViewTop5paciente>> top5pacientes() {
        List<ViewTop5paciente> totalPacientes = service.top5pacientes();
        if (totalPacientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(totalPacientes);
    }

    @Operation(summary = "Listar pacientes ativos por funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes do funcionário retornada"),
            @ApiResponse(responseCode = "204", description = "O funcionário não possui pacientes")
    })
    @GetMapping("/funcionario/listarAtivos/{idFuncionario}")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<List<PacienteResponseBody>> listarPacientesAtivosPorFuncionario(@PathVariable Integer idFuncionario) {
        List<PacienteResponseBody> lista = PacienteMapper.toResponseList(service.listarPacientesAtivosPorFuncionario(idFuncionario));
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Grafico novos pacientes por mês no ano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do gráfico retornados")
    })
    @GetMapping("/graficoNovosPacientesPorMes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoNovosPacientesPorMes>> graficoNovosPacientesMes () {
        return ResponseEntity.status(200).body(service.graficoNovosPacientesPorMes());
    }

    @Operation(summary = "Grafico retencao por mês")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do gráfico retornados")
    })
    @GetMapping("/graficoTaxaRetencaoMes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoTaxaRetencaoMes>> graficoTaxaRetencaoMes () {
        return ResponseEntity.status(200).body(service.graficoTaxaRetencaoMes());
    }


    @Operation(summary = "kpi taxa retencao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do gráfico retornados")
    })
    @GetMapping("/kpiTaxaRetencao")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<BigDecimal> kpiTaxaRetencao () {
        return ResponseEntity.status(200).body(service.taxaRetencao());
    }

    @GetMapping("/buscarPorTermo/{termo}")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<List<PacienteResponseBody>> buscarPorTermo(@PathVariable String termo){
        List<PacienteResponseBody> response = PacienteMapper.toResponseList(service.buscarPorTermo(termo));
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/buscarPorStatus")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO',)")
    public ResponseEntity<List<PacienteResponseBody>> buscarPorStatus(@RequestParam boolean ativo) {
        List<PacienteResponseBody> response = PacienteMapper.toResponseList(service.buscarPorStatus(ativo));
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/buscarPorUsuario/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE')")
    public ResponseEntity<PacienteResponseBody> buscarPacientePorIdUsuario(@PathVariable Integer idUsuario) {
        PacienteResponseBody response = PacienteMapper.toResponse(service.buscarPorIdOuThrowUsuario(idUsuario));
        return ResponseEntity.status(200).body(response);
    }


}

