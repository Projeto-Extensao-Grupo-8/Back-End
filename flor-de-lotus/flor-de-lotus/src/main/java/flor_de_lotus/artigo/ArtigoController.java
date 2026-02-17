package flor_de_lotus.artigo;

import flor_de_lotus.artigo.dto.ArtigoMapper;
import flor_de_lotus.artigo.dto.ArtigoPostRequest;
import flor_de_lotus.artigo.dto.ArtigoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/artigos")
@Tag(name = "Artigos", description = "Endpoints utilizados para gerenciar os artigos")
public class ArtigoController {

    private final ArtigoService artigoService;
    @Operation(summary = "Listar Todos Artigos")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'USUARIO', 'PACIENTE')")
    public ResponseEntity<List<ArtigoResponse>> listarTodos() {
        return ResponseEntity.ok(ArtigoMapper.toResponseList(artigoService.listarTodos()));
    }

    @Operation(summary = "Procurar artigo por palavra")
    @GetMapping("/pesquisar")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'USUARIO', 'PACIENTE')")
    public ResponseEntity<List<ArtigoResponse>> pesquisar(
            @RequestParam(value = "termo", required = false) String termo) {
        return ResponseEntity.ok(ArtigoMapper.toResponseList(artigoService.pesquisar(termo)));
    }

    @Operation(summary = "Procurar Artigo por Id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'USUARIO', 'PACIENTE')")
    public ResponseEntity<ArtigoResponse> buscarPorId(@PathVariable Integer id) {

        Optional<ArtigoResponse> artigo = artigoService.buscarPorId(id).map(ArtigoMapper::toResponse);

        return artigo
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novos artigos no sistema")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ArtigoResponse> cadastrar(@Valid @RequestBody ArtigoPostRequest dto) {

        Artigo artigo = ArtigoMapper.toEntity(dto);

        try {
            ArtigoResponse criado = ArtigoMapper.toResponse(artigoService.cadastrar(artigo, dto.getIdFuncionario()));
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(summary = "Atualizar Artigo por ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ArtigoResponse> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ArtigoPostRequest dto) {

        Artigo atualizacao = ArtigoMapper.toEntity(dto);

        Optional<ArtigoResponse> artigoOptional = artigoService.atualizar(id, atualizacao, dto.getIdFuncionario()).map(ArtigoMapper::toResponse);

        return artigoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar Artigo por ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        artigoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}