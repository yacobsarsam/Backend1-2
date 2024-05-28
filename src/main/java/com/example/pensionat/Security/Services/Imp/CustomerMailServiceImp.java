package com.example.pensionat.Security.Services.Imp;

import com.example.pensionat.Models.Bokning;
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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class CustomerMailServiceImp implements CustomerMailService {

    private final JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Override
    public void sendConfirmationMail(Bokning b) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

//        String htmlMsg = "<h3>'temporary HTML mail message'</h3>"; //ändra till html-fil?

        String htmlMsg = renderTemplate("admin/htmlConfirmationMailTemplate", b);

        helper.setText(htmlMsg, true);
        helper.setTo(b.getKund().getEmail());
        helper.setSubject("Bokningsbekräftelse - Pensionatet");
        helper.setFrom("noreply@example.com");
        mailSender.send(mimeMessage);
    }

    public String renderTemplate(String templateName, Bokning b) throws IOException {
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
        variables.put("showName", getMailProperties().getProperty("showName"));
        variables.put("showEmail", getMailProperties().getProperty("showEmail"));
        variables.put("showPhoneNumber", getMailProperties().getProperty("showPhoneNumber"));
        variables.put("showRoomNumber", getMailProperties().getProperty("showRoomNumber"));
        variables.put("showDate", getMailProperties().getProperty("showDate"));
        variables.put("showTotalPrice", getMailProperties().getProperty("showTotalPrice"));
        variables.put("showNumOfBeds", getMailProperties().getProperty("showNumOfBeds"));
        return renderTemplate(templateName, variables);
    }

    private String renderTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }

    public void alterMailProperties(String ROOMNUMBER, String DATES, String NUMOFBEDS, String PRICE, String NAME, String PHONENUMBER, String EMAIL) throws IOException {
        String mailPropertiesPath = "src/main/java/com/example/pensionat/Properties/configure.mail.properties";

        boolean roomNumber = "on".equals(ROOMNUMBER);
        boolean dates = "on".equals(DATES);
        boolean numOfBeds = "on".equals(NUMOFBEDS);
        boolean price = "on".equals(PRICE);
        boolean name = "on".equals(NAME);
        boolean phoneNumber = "on".equals(PHONENUMBER);
        boolean email = "on".equals(EMAIL);

        Properties mailProperties = getMailProperties();

        updateMailProperties(mailProperties, roomNumber, dates, numOfBeds, price, name, phoneNumber, email);

        saveMailProperties(mailProperties);

        System.out.println("properties updated");
    }

    public Properties getMailProperties() throws IOException {
        String mailPropertiesPath = "src/main/java/com/example/pensionat/Properties/configure.mail.properties";

        Properties mailProperties = new Properties();
        mailProperties.load(new FileInputStream(mailPropertiesPath));
        return mailProperties;
    }

    public void updateMailProperties(Properties mailProperties, boolean roomNumber, boolean dates, boolean numOfBeds, boolean price, boolean name, boolean phoneNumber, boolean email) {
        mailProperties.setProperty("showRoomNumber", String.valueOf(roomNumber));
        mailProperties.setProperty("showDate", String.valueOf(dates));
        mailProperties.setProperty("showNumOfBeds", String.valueOf(numOfBeds));
        mailProperties.setProperty("showTotalPrice", String.valueOf(price));
        mailProperties.setProperty("showName", String.valueOf(name));
        mailProperties.setProperty("showPhoneNumber", String.valueOf(phoneNumber));
        mailProperties.setProperty("showEmail", String.valueOf(email));
    }

    public void saveMailProperties(Properties mailProperties) {
        String mailPropertiesPath = "src/main/java/com/example/pensionat/Properties/configure.mail.properties";
        try (FileOutputStream out = new FileOutputStream(mailPropertiesPath)) {
            mailProperties.store(out, null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


