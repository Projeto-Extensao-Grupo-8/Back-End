package flor_de_lotus.teste;

import flor_de_lotus.teste.StatusEstoque;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Teste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTeste;

    private String codigo;
    private String nome;
    private String categoria;
    private String subCategoria;
    private String editora;
    private String tipo;
    private Double preco;
    private Integer estoqueMinimo;
    private LocalDate validade;
    private Integer qtd;

    @Enumerated(EnumType.STRING)
    private StatusEstoque statusEstoque;

    public void verificarStatusEstoque() {
        if (this.qtd != null && this.estoqueMinimo != null) {
            if (this.qtd <= this.estoqueMinimo) {
                this.statusEstoque = StatusEstoque.CRITICO;
            } else {
                this.statusEstoque = StatusEstoque.OK;
            }
        }
    }

    /**
     * O JPA vai chamar este método automaticamente antes de criar (@PrePersist) 
     * ou atualizar (@PreUpdate) o Teste no banco de dados.
     */
    @PrePersist
    @PreUpdate
    private void antesDeSalvar() {
        verificarStatusEstoque();
    }
}