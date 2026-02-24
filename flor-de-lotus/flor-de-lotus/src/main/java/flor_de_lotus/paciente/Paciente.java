package flor_de_lotus.paciente;

import flor_de_lotus.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    private boolean ativo = true;

    @OneToOne
    @JoinColumn(name = "fk_usuario_id", nullable = false)
    private Usuario fkUsuario;


    public String getNome() {
        return fkUsuario != null ? fkUsuario.getNome() : null;
    }


    public Integer getIdUsuario() {
        return fkUsuario != null ? fkUsuario.getIdUsuario() : null;
    }

}
