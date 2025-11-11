package flor_de_lotus.artigo.Event;

import flor_de_lotus.artigo.Artigo;
import flor_de_lotus.email.EmailService;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.util.List;

@Component
public class ArtigoCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(ArtigoCreatedListener.class);

    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    public ArtigoCreatedListener(EmailService emailService, UsuarioRepository usuarioRepository) {
        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Listener assíncrono que roda após o commit da transação que criou o Artigo.
     * @param event evento contendo o artigo salvo
     */
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onArtigoCreated(ArtigoCreatedEvent event) {
        log.info("Listener acionado para artigo id={}", event.getArtigo().getIdArtigo());
        Artigo artigo = event.getArtigo();
        log.info("Iniciando notificação de novo artigo (id={}) para usuários", artigo.getIdArtigo());

        List<Usuario> usuarios = usuarioRepository.findAll();

        String assunto = "Novo artigo publicado: " + artigo.getTitulo();

        for (Usuario u : usuarios) {
            if (u == null) continue;

            if (u.getNewsletter().booleanValue() == true) {
                String email = u.getEmail();
                if (email == null || email.isBlank()) {
                    log.debug("Usuário {} sem e-mail — pulando", u.getIdUsuario());
                    continue;
                }

                String corpo = buildCorpoEmail(u, artigo);

                try {
                    emailService.enviarEmailSimples(email, assunto, corpo);
                    log.debug("E-mail enviado para {}", email);
                } catch (Exception ex) {
                    log.error("Falha ao enviar e-mail para {}: {}", email, ex.getMessage(), ex);
                }
            }

        }

        log.info("Processo de notificação finalizado para artigo id={}", artigo.getIdArtigo());
    }

    private String buildCorpoEmail(Usuario usuario, Artigo artigo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Olá ").append(usuario.getNome()).append(",\n\n");
        sb.append("Um novo artigo foi publicado no Flor de Lótus:\n\n");
        sb.append("Título: ").append(artigo.getTitulo()).append("\n");
        sb.append("Data: ").append(artigo.getDtPublicacao()).append("\n\n");
        sb.append(artigo.getDescricao()).append("\n\n");
        sb.append("Atenciosamente,\nEquipe Flor de Lótus");
        return sb.toString();
    }
}
