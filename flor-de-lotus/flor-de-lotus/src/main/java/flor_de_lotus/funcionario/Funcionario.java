package flor_de_lotus.funcionario;

import flor_de_lotus.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;

    @Column(unique = true)
    private String crp;

    private String especialidade;

    private LocalDate dtAdmissao;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario fkUsuario;

    public String getNome() {
        return fkUsuario != null ? fkUsuario.getNome() : null;
    }

    public Integer getIdUsuario() {
        return fkUsuario != null ? fkUsuario.getIdUsuario() : null;
    }
}
