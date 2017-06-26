
package model.User;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rohanpandit
 */
public class RegistrationConf {
    public void registrationConf(String name, String email){
                final String username = "fantasytenniswebapp@gmail.com";
        final String password = "fantasytennis100";
        
          // Recipient's email ID needs to be mentioned.
        String to = email;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Registration Confirmation");

            // Now set the actual message
            message.setText("Dear " + name + ",\n \n"
                    + "You are now registered to participate in this year's Fantasy Tennis Competition.\n\n"
                    + "Thank you for taking the time to register and good luck, may the best tennis fan win!\n\n"
                    + "Sincerely,\n"
                    + "Fantasy Tennis Web App Team\n\n"
                    + "*This email is not monitored, do not reply.*");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
}
