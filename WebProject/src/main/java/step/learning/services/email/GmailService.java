package step.learning.services.email;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class GmailService implements EmailService {
    @Override
    public boolean send(String to, String subject, String text) {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls", "true");
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        emailProperties.put("mail.smtp.ssl.protocol", "TLSv1.2");

        Session emailSession = Session.getInstance(emailProperties);
        emailSession.setDebug(true);

        Transport emailTransport;
        try {
            emailTransport = emailSession.getTransport("smtp");
            emailTransport.connect("smtp.gmail.com", "milleriscream@gmail.com", "dejugtqdudfejbkm");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }
}