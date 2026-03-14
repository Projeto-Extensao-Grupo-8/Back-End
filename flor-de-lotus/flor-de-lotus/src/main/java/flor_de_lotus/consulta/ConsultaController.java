package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.*;
import flor_de_lotus.consulta.dto.dashAgendamento.GraficoDesempenhoSemanal;
import flor_de_lotus.consulta.dto.dashAgendamento.GraficoDistribuicaoHorario;
import flor_de_lotus.consulta.dto.dashAgendamento.KpiCancelamentos;
import flor_de_lotus.consulta.dto.dashAgendamento.KpisDashAgendamentosResponse;
import flor_de_lotus.consulta.dto.dashFinanceiro.GraficoConsultaMes;
import flor_de_lotus.consulta.dto.dashFinanceiro.GraficoFaturamentoMensal;
import flor_de_lotus.consulta.dto.dashFinanceiro.KpiMelhorFaturamentoAno;
import flor_de_lotus.consulta.dto.dashFinanceiro.KpisDashFinanceiroResponse;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE')")
    public ResponseEntity<ConsultaResponseBody> cadastrar(@RequestBody @Valid ConsultaPostRequestBody body) {

        Consulta consulta = ConsultaMapper.toEntity(body);

        Consulta cadastrada = service.cadastrar(consulta, body.getFkUsuario(), body.getFkFuncionario());

        ConsultaResponseBody response = ConsultaMapper.toResponse(cadastrada);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todas as consultas cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
            @ApiResponse(responseCode = "204", description = "Não há consultas cadastradas")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ConsultaResponseBody>> listarTodas() {
        List<ConsultaResponseBody> lista = ConsultaMapper.toResponseList(service.listarTodas());
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<ConsultaResponseBody> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ConsultaMapper.toResponse(service.buscarPorIdOuThrow(id)));
    }

    @Operation(summary = "Deletar consulta via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<ConsultaResponseBody> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody @Valid ConsultaPostRequestBody body) {

        Consulta atualizacao = ConsultaMapper.toEntity(body);

        Consulta consultaAtualizada = service.atualizarParcial(id, atualizacao, body.getFkFuncionario(), body.getFkUsuario());

        ConsultaResponseBody response = ConsultaMapper.toResponse(consultaAtualizada);

        return ResponseEntity.status(200).body(response);

    }

    @Operation(summary = "Listar consultas de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
            @ApiResponse(responseCode = "204", description = "Paciente não possui consultas cadastradas")
    })
    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE')")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<ConsultaResponseBody> lista = service.listarPorPaciente(idPaciente);
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Listar consultas de um funcionário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
            @ApiResponse(responseCode = "204", description = "Funcionário não possui consultas cadastradas")
    })
    @GetMapping("/funcionario/{idFuncionario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<ConsultaResponseBody> lista = service.listarPorFuncionario(idFuncionario);
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Listar próximas consultas de um funcionário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class))))
    })
    @GetMapping("funcionarioConsultas/{idFuncionario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorProximasConsultasFuncionario(@PathVariable Integer idFuncionario) {
        List<ConsultaResponseBody> lista = ConsultaMapper.toResponseList(service.listarProximasConsultasFuncionario(idFuncionario));
        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Quantidade de consultas realizadas de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("qtdPacienteConsultas/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<Integer> qtdSessoesRealizadasDoPaciente(@PathVariable Integer idPaciente) {
        Integer qtdSessoesRealizadas = service.qtdSessoesRealizadasDoPaciente(idPaciente);
        return ResponseEntity.status(200).body(qtdSessoesRealizadas);
    }

    @Operation(summary = "Quantidade de consultas realizadas de um funionario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("qtdFuncionarioConsultas/{idFuncionario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<Integer> qtdSessoesRealizadasDoFuncionario(@PathVariable Integer idFuncionario) {
        Integer qtdSessoesRealizadas = service.qtdSessoesRealizadasDoFuncionario(idFuncionario);
        return ResponseEntity.status(200).body(qtdSessoesRealizadas);
    }

    @Operation(summary = "Listar próximas consultas de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("pacienteConsultas/{idPaciente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO','PACIENTE')")
    public ResponseEntity<List<ConsultaResponseBody>> listarPorProximasConsultasPacientes(@PathVariable Integer idPaciente) {
        List<ConsultaResponseBody> lista = ConsultaMapper.toResponseList(service.listarProximasConsultaPaciente(idPaciente));
        return ResponseEntity.status(200).body(lista);
    }
    @Operation(summary = "Grafico de desempenho semanal das consultas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/graficoDesempenhoSemanal")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoDesempenhoSemanal>> graficoDesempenhoSemanal() {
        return ResponseEntity.status(200).body(service.graficoDesempenhoSemanal());
    }

    @Operation(summary = "Grafico de distribuição de horários das consultas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/graficoPeriodo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoDistribuicaoHorario>> graficoDistruibuicaoHorario() {
        return ResponseEntity.status(200).body(service.graficoDistribuicaoHorario());
    }

    @Operation(summary = "Dados da kpi de cancelamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpiCancelamentos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<KpiCancelamentos>> kpiCancelamentos() {
        return ResponseEntity.status(200).body(service.kpiCancelamentos());
    }

    @Operation(summary = "Kpi da DashBoard de agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpisDashboardAgendamentos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<KpisDashAgendamentosResponse> kpisDashboardAgendamentos() {
        Long kpiAgendamentosSemana = service.kpiAgendamentosSemana();
        String kpiTaxaComparecimento = service.kpiTaxaComparecimento();
        Long kpiConsultasRealizadas = service.kpiConsultasRealizadas();
        List<KpiCancelamentos> kpiCancelamentos = service.kpiCancelamentos();

        KpisDashAgendamentosResponse response = new KpisDashAgendamentosResponse(kpiAgendamentosSemana, kpiTaxaComparecimento, kpiConsultasRealizadas, kpiCancelamentos);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Grafico consultas por mes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/graficoConsultasMes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoConsultaMes>> graficoConultaMes() {
        return ResponseEntity.status(200).body(service.graficoConsultaMes());
    }

    @Operation(summary = "Grafico faturamento mensal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/graficoFaturamentoMensal")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GraficoFaturamentoMensal>> graficoFaturamentoMensal() {
        return ResponseEntity.status(200).body(service.graficoFaturamentoMensal());
    }

    @Operation(summary = "Kpi da DashBoard de Financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpisDashboardFinanceira")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<KpisDashFinanceiroResponse> kpisDashboardFinanceiro() {
        Double KpiFaturamentoMes = service.kpiFaturamentoMes();
        Double KpiFaturamentoAno = service.kpiFaturamentoAno();
        List<KpiMelhorFaturamentoAno> kpiMelhorFaturamentoAno = service.kpiMelhorFaturamentoAno();

        KpisDashFinanceiroResponse response = new KpisDashFinanceiroResponse(KpiFaturamentoMes, KpiFaturamentoAno, kpiMelhorFaturamentoAno);

        return ResponseEntity.status(200).body(response);
    }



}
