package com.solidarize.notification.mail;

import com.solidarize.notification.client.SolidarizeEvent;
import com.solidarize.notification.service.SolidarizeEventsService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Service
public class MailSender {

    @Value("${email.user.name}")
    private String USER_NAME;
    @Value("${email.user.password}")
    private String PASSWORD;
    @Value("${smtp.server}")
    private String smtpServer;
    @Value("${smtp.port}")
    private String smtpPort;
    private SolidarizeEventsService solidarizeEventsService;
    private TemplateEngine templateEngine;

    @Autowired
    public MailSender(SolidarizeEventsService solidarizeEventsService, TemplateEngine templateEngine) {
        this.solidarizeEventsService = solidarizeEventsService;
        this.templateEngine = templateEngine;
    }

    public void send(String sendTo) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {sendTo};
        String subject = "Solidarize";
        String body = buildMailBody();
        System.out.println("mail user name: " + from);
        try {
            sendFromGMail(from, pass, to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildMailBody() {
        List<SolidarizeEvent> latestEvents = solidarizeEventsService.getLatestEvents();
        Context context = new Context();
        context.setVariable("event1Title", latestEvents.get(0).getTitle());
        context.setVariable("event1Date", latestEvents.get(0).getEvent_time());
        context.setVariable("event2Title", latestEvents.get(1).getTitle());
        context.setVariable("event2Description", latestEvents.get(1).getDescription());
        context.setVariable("event3Title", latestEvents.get(2).getTitle());
        context.setVariable("event3Description", latestEvents.get(2).getDescription());
        context.setVariable("imageResourceName", "evento.jpg");
        context.setVariable("imageResourceName", "facebook.png");
        context.setVariable("imageResourceName", "twitter.png");
        return templateEngine.process("email", context);
    }

    private void sendFromGMail(String from, String pass, String[] to, String subject, String body) throws MessagingException {
        String host = smtpServer;
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage messageMime = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(messageMime, true, "utf-8");

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.setTo(toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body,true);

            byte[] image = getImageByte("/evento.jpg");
            if (image != null) {
                message.addInline("evento.jpg", new ByteArrayResource(image), "image/jpeg");
            }
            byte[] imageFacebook = getImageByte("/facebook.png");
            if (imageFacebook != null) {
                message.addInline("facebook.png", new ByteArrayResource(imageFacebook), "image/png");
            }
            byte[] imageTwitter = getImageByte("/twitter.png");
            if (imageTwitter != null) {
                message.addInline("twitter.png", new ByteArrayResource(imageTwitter), "image/png");
            }

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(messageMime, messageMime.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }


    }

    public byte[] getImageByte(String fileName) {
        byte[] image;
        try {
            InputStream is = this.getClass().getResourceAsStream(fileName);
            image = IOUtils.toByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

}

