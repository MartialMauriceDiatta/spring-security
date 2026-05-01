package sn.martial.avis.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sn.martial.avis.entite.Validation;


@AllArgsConstructor
@Service
public class NotificationService {
    JavaMailSender javaMailSender;

    public void envoyer(Validation validation) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom("martialmauricediatta@gmail.com");
            helper.setTo(validation.getUtilisateur().getEmail());
            helper.setSubject("🔐 Votre code d'activation - Bienvenue !");

            String htmlContent = String.format(
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<style>" +
                            "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                            "    .container { max-width: 600px; margin: 40px auto; background: white; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); overflow: hidden; }" +
                            "    .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 40px 20px; text-align: center; }" +
                            "    .header h1 { margin: 0; font-size: 28px; font-weight: 600; }" +
                            "    .content { padding: 40px 30px; }" +
                            "    .greeting { font-size: 18px; color: #333; margin-bottom: 20px; }" +
                            "    .code-container { background: #f8f9fa; border: 2px dashed #667eea; border-radius: 12px; padding: 30px; text-align: center; margin: 30px 0; }" +
                            "    .code { font-size: 42px; font-weight: bold; color: #667eea; letter-spacing: 8px; font-family: 'Courier New', monospace; }" +
                            "    .message { color: #666; line-height: 1.6; margin-bottom: 20px; }" +
                            "    .footer { background: #f8f9fa; padding: 20px; text-align: center; color: #999; font-size: 12px; border-top: 1px solid #e0e0e0; }" +
                            "    .button { display: inline-block; background: #667eea; color: white; padding: 12px 30px; border-radius: 6px; text-decoration: none; margin-top: 20px; }" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class='container'>" +
                            "    <div class='header'>" +
                            "        <h1>✨ Activation du compte ✨</h1>" +
                            "    </div>" +
                            "    <div class='content'>" +
                            "        <div class='greeting'>Bonjour <strong>%s %s</strong>,</div>" +
                            "        <div class='message'>" +
                            "            Merci de vous être inscrit ! Pour finaliser la création de votre compte, " +
                            "            veuillez utiliser le code d'activation ci-dessous :" +
                            "        </div>" +
                            "        <div class='code-container'>" +
                            "            <div class='code'>%s</div>" +
                            "            <p style='margin-top: 15px; color: #888; font-size: 14px;'>Ce code est valable 24 heures</p>" +
                            "        </div>" +
                            "        <div class='message'>" +
                            "            Si vous n'avez pas demandé cette activation, veuillez ignorer cet email.<br>" +
                            "            Pour toute assistance, contactez notre support." +
                            "        </div>" +
                            "    </div>" +
                            "    <div class='footer'>" +
                            "        <p>© 2024 Votre Application - Tous droits réservés</p>" +
                            "        <p>Cet email a été envoyé automatiquement, merci de ne pas y répondre.</p>" +
                            "    </div>" +
                            "</div>" +
                            "</body>" +
                            "</html>",
                    validation.getUtilisateur().getNom(),
                    validation.getUtilisateur().getPrenom(),
                    validation.getCode()
            );

            helper.setText(htmlContent, true); // true = HTML
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer l'erreur d'envoi
        }
    }


}
