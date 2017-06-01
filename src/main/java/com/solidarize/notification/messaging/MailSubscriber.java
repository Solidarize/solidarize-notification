package com.solidarize.notification.messaging;

import com.solidarize.notification.mail.MailSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MailSubscriber {

    private MailSender mailSender;

    @Autowired
    public MailSubscriber(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "#{@mailQueue}")
    public void mailListener(@Payload MailQueued mailQueued) {
        mailSender.send(mailQueued.mail);
    }

    static class MailQueued {
        private String mail;

        public MailQueued() {
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
    }
}
