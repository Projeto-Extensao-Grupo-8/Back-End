package flor_de_lotus.artigo;

import flor_de_lotus.funcionario.Funcionario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Artigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArtigo;

    @Schema(description = "Descrição (Conteúdo do artigo)")
    private String descricao;

    @Schema(description = "Título do artigo")
    private String titulo;

    @Schema(description = "Data de publicação do artigo")
    private LocalDate dtPublicacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Funcionario", nullable = false)
    private Funcionario fkFuncionario;
}