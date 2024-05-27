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
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public void updateMailProperties(String ROOMNUMBER, String DATES, String NUMOFBEDS, String PRICE, String NAME, String PHONENUMBER, String EMAIL) {
        Properties properties = new Properties();
        String propertiesPath = "C:\\Users\\Eriic\\Javamapp\\Backend\\Pensionat\\src\\main\\java\\com\\example\\pensionat\\Properties\\ConfirmationMailProperties.java";

        //---------------------------
        //Do the below "on.equals(...)" on all of the inputs and it will probably work. Too tired now...
        //---------------------------

        boolean roomNumber = "on".equals(ROOMNUMBER);
        System.out.println(roomNumber);

        try (FileInputStream in = new FileInputStream(propertiesPath)) {
            properties.load(in);
            properties.setProperty("showRoomNumber", ROOMNUMBER);
            properties.setProperty("showDates", DATES);
            properties.setProperty("showNumOfBeds", NUMOFBEDS);
            properties.setProperty("showTotalPrice", PRICE);
            properties.setProperty("showName", NAME);
            properties.setProperty("showPhoneNumber", PHONENUMBER);
            properties.setProperty("showEmail", EMAIL);
            System.out.println("Har uppdaterat alla properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream out = new FileOutputStream(propertiesPath)) {
            properties.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


