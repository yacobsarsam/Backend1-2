package com.example.pensionat;

import com.example.pensionat.Models.*;
import com.example.pensionat.Properties.QueueProperties;
import com.example.pensionat.Repositories.RumEventRepository;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class RoomEventApplication implements CommandLineRunner {

    @Autowired
    private QueueProperties queueProperties;
    private final ObjectMapper objectMapper;
    private final RumEventRepository rumEventRepository;
    private final RumRepo rumRepository;
    private final RumService rumService;
    @Override
    public void run(String... args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueProperties.getHost());
        factory.setUsername(queueProperties.getUsername());
        factory.setPassword(queueProperties.getPassword());

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            String queueName = queueProperties.getUsername();
            channel.queueDeclare(queueName, false, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicConsume(queueName, true, createDeliverCallback(), consumerTag -> {});

            Random random = new Random();
            LocalDateTime now = LocalDateTime.now();

            for (int roomNumber = 1; roomNumber <= 12; roomNumber++) {
                LocalDateTime randomDate = now.minusDays(random.nextInt(365));
                sendEvent(new RoomOpened(roomNumber, randomDate, "Door opened", "Per Persson"));
                sendEvent(new RoomClosed(roomNumber, randomDate, "Door closed", "Per Persson"));
                sendEvent(new RoomCleaningStarted(roomNumber, randomDate, "Cleaning started", "Mattias Larson"));
                sendEvent(new RoomCleaningFinished(roomNumber, randomDate, "Cleaning finished", "Mattias Larson"));
            }
        }
    }

    private DeliverCallback createDeliverCallback() {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            RumEvent rumEvent = convertToRumEvent(message);

            int roomNumber = rumEvent.getRoomNumber();
            Long rumId = Long.valueOf(roomNumber);

            Rum rum = rumRepository.findById(rumId).orElse(null);

            if (rum != null) {
                rumEvent.setRum(rum);
                rumEventRepository.save(rumEvent);
            } else {
                System.out.println("Rum not found.");
            }
        };
    }

    private RumEvent convertToRumEvent(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, RumEvent.class);
    }

    private void sendEvent(RumEvent event) {
        try {
            sendEventToQueue(event);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void sendEventToQueue(RumEvent event) throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueProperties.getHost());
        factory.setUsername(queueProperties.getUsername());
        factory.setPassword(queueProperties.getPassword());

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            String queueName = queueProperties.getUsername();
            channel.queueDeclare(queueName, false, false, false, null);
            String message = objectMapper.writeValueAsString(event);
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
