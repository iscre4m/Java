package step.learning.services.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailService implements EmailService {
    @Override
    public boolean send(String to, String subject, String text) {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        emailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session emailSession = Session.getInstance(emailProperties);
        emailSession.setDebug(true);

        Transport emailTransport;
        try {
            emailTransport = emailSession.getTransport("smtp");
            emailTransport.connect("smtp.gmail.com", "milleriscream@gmail.com", "dejugtqdudfejbkm");

            MimeMessage message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress("milleriscream@gmail.com"));
            message.setSubject(subject);
            message.setContent(text, "text/html;charset=utf-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            emailTransport.sendMessage(message, InternetAddress.parse(to));
            emailTransport.close();
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }
}