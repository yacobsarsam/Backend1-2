package com.example.pensionat.Security.Services.Imp;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Security.Services.CustomerMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMailServiceImp implements CustomerMailService {
    private final JavaMailSender mailSender;


    @Override
    public void sendConfirmationMail(Bokning b) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<h3>'temporary HTML mail message'</h3>"; //ändra till html-fil?

        helper.setText(htmlMsg, true);
        helper.setTo(b.getKund().getEmail());
        helper.setSubject("Bokningsbekräftelse - Pensionatet");
        helper.setFrom("noreply@example.com");
        mailSender.send(mimeMessage);
    }
}
