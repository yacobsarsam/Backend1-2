package com.example.pensionat.Security.Services.Imp;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Properties.ConfirmationMailProperties;
import com.example.pensionat.Properties.MailProperties;
import com.example.pensionat.Security.Services.CustomerMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerMailServiceImp implements CustomerMailService {

    private final JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private ConfirmationMailProperties confirmationMailProperties;


    @Override
    public void sendConfirmationMail(Bokning b) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

//        String htmlMsg = "<h3>'temporary HTML mail message'</h3>"; //ändra till html-fil?

        String htmlMsg = renderTemplate("htmlConfirmationMailTemplate", b);

        helper.setText(htmlMsg, true);
        helper.setTo(b.getKund().getEmail());
        helper.setSubject("Bokningsbekräftelse - Pensionatet");
        helper.setFrom("noreply@example.com");
        mailSender.send(mimeMessage);
    }

    public String renderTemplate(String templateName, Bokning b) {
        Map<String, Object> variables = new HashMap<>();
//        variables.put("title", "Bokningsbekräftelse");
        variables.put("name", b.getKund().getNamn());
        variables.put("email", b.getKund().getEmail());
        variables.put("phoneNumber", b.getKund().getTel());
//        variables.put("doubleRoom", b.getRum().isDubbelrum());
        variables.put("roomNumber", b.getRum().getRumsnr());
//        variables.put("price", b.getRum().getPrice());
        variables.put("startDate", b.getStartdatum());
        variables.put("endDate", b.getSlutdatum());
        variables.put("totalPrice", b.getTotalPrice());
        variables.put("numOfBeds", b.getNumOfBeds());
        variables.put("date", new java.util.Date());
        variables.put("showName", confirmationMailProperties.getShowName());
        variables.put("showEmail", confirmationMailProperties.getShowEmail());
        variables.put("showPhoneNumber", confirmationMailProperties.getShowPhoneNumber());
        variables.put("showRoomNumber", confirmationMailProperties.getShowRoomNumber());
        variables.put("showDate", confirmationMailProperties.getShowDate());
        variables.put("showTotalPrice", confirmationMailProperties.getShowTotalPrice());
        variables.put("showNumOfBeds", confirmationMailProperties.getShowNumOfBeds());
        return renderTemplate(templateName, variables);
    }

    public String renderTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}


