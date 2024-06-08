package com.team2final.minglecrm.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender mailSender;

    public void sendMail(String toEmail,
                         String subject,
                         String content) throws MessagingException {

        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(emailFrom);
        mailHelper.setTo(toEmail);
        mailHelper.setSubject(subject);
        mailHelper.setText(content, true);

        mailSender.send(mail);
    }
}
