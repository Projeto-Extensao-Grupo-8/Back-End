package flor_de_lotus.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailSimples(String para, String assunto, String corpo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(para);
        msg.setSubject(assunto);
        msg.setText(corpo);
        mailSender.send(msg);
    }
}
