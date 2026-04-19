package flor_de_lotus.paciente;

import flor_de_lotus.consulta.ConsultaRepository;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.paciente.dto.PacientePostRequestBody;
import flor_de_lotus.paciente.dto.dashPaciente.GraficoNovosPacientesPorMes;
import flor_de_lotus.paciente.dto.dashPaciente.GraficoTaxaRetencaoMes;
import flor_de_lotus.paciente.dto.dashPaciente.ViewTop5paciente;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioRepository;
import flor_de_lotus.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final UsuarioRepository userRepository;
    private final PacienteRepository repository;
    private final UsuarioService usuarioService;
    private final ConsultaRepository consultaRepository;


    public Paciente cadastrar(Integer idUsuario) {

        Usuario usuario = usuarioService.buscarEntidadePorIdOuThrow(idUsuario);

        if (repository.existsByFkUsuario_IdUsuario(idUsuario)) {
            throw new EntidadeConflitoException("Já existe um paciente cadastrado para este usuário.");
        }

        usuario.setNivelPermissao("2");
        userRepository.save(usuario);
        Paciente entity = new Paciente();
        entity.setFkUsuario(usuario);

        return repository.save(entity);
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPorIdOuThrow(Integer id) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if (pacienteOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return pacienteOpt.get();
    }

    public Paciente atualizarParcial(Integer id, PacientePostRequestBody dto) {
        Paciente paciente = buscarPorIdOuThrow(id);

        if (dto.getAtivo() != null) {
            paciente.setAtivo(dto.getAtivo());
        }


        return repository.save(paciente);
    }

    public void deletarPorId(Integer id) {
        repository.delete(buscarPorIdOuThrow(id));
    }

    public List<Paciente> listarPacientesPorFuncionario(Integer idFuncionario) {
        return repository.findPacientesByFkFuncionario_IdFuncionario(idFuncionario);
    }

    public List<Paciente> listarPacientesPorFuncionarioOffset(Integer idFuncionario, int pagina, int tamanho) {
        int offset = Math.max((pagina - 1) * tamanho, 0);
        return repository.findPacientesByFkFuncionario_IdFuncionarioWithPagination(idFuncionario, tamanho, offset);
    }

    public Long totalPacientes() {
        return repository.totalPacientes().getTotalPacientesAtivos();
    }

    public  Long totalPacientesPorAno(Integer ano) {
        return repository.totalPacientesNoAno(ano).getQtd();
    }

    public List<ViewTop5paciente> top5pacientes() {
        return repository.top5Pacientes();
    }

    public List<Paciente> listarPacientesAtivosPorFuncionario(Integer idFuncionario) {
        return repository.findPacientesAtivosByFkFuncionario(idFuncionario);
    }

    public List<GraficoNovosPacientesPorMes> graficoNovosPacientesPorMes(){
        return repository.graficoNovosPacientesPorMes();
    }

    public List<GraficoTaxaRetencaoMes> graficoTaxaRetencaoMes(){
        return repository.graficoTaxaRetencaoMes();
    }

    public BigDecimal taxaRetencao() {
        return repository.kpiTaxaRetencao().getTaxaPercentual();
    }

    public List<Paciente> buscarPorTermo(String termo) {
        return repository.findByTermo(termo);
    }

    public List<Paciente> buscarPorStatus(boolean ativo) {
        return repository.findByAtivo(ativo);
    }

    public Paciente buscarPorIdOuThrowUsuario(Integer id) {
        Optional<Paciente> pacienteOpt = repository.findByFkUsuario_IdUsuario(id);
        if (pacienteOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return pacienteOpt.get();
    }

    public Integer qtdSessoesPacientePorFuncionario(Integer idPaciente, Integer idFuncionario) {
        return consultaRepository.qtdSessoesPacientePorFuncionario(idPaciente, idFuncionario);
    }

    public String buscarProximaConsultaPacientePorFuncionario(Integer idPaciente, Integer idFuncionario) {
        return consultaRepository.buscarProximaConsultaPacientePorFuncionario(idPaciente, idFuncionario);
    }
}
