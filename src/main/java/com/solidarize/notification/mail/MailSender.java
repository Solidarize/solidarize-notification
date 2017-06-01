package com.solidarize.notification.mail;

import com.solidarize.notification.client.SolidarizeEvent;
import com.solidarize.notification.service.SolidarizeEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MailSender {

    @Value("${emailUserName}")
    private String USER_NAME;
    @Value("${emailUserPassword}")
    private String PASSWORD;
    private SolidarizeEventsService solidarizeEventsService;

    @Autowired
    public MailSender(SolidarizeEventsService solidarizeEventsService) {
        this.solidarizeEventsService = solidarizeEventsService;
    }

    public void send(String sendTo) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {sendTo};
        String subject = "Solidarize";
        String body = buildMailBody();
        System.out.println("mail user name: "+ from);
        sendFromGMail(from, pass, to, subject, body);
    }

    private String buildMailBody() {
        List<SolidarizeEvent> latestEvents = solidarizeEventsService.getLatestEvents();
        final String subject = "\"Olá, \nSegue abaixo a lista de eventos para os proximos dias:";
        String listOfEvents = latestEvents.stream().map(solidarizeEvent ->
                " \n- " + solidarizeEvent.getTitle()
        ).collect(Collectors.joining());
        return subject + listOfEvents;
    }

    private void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.sendgrid.net";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
