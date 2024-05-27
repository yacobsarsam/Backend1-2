package com.example.pensionat;

import com.example.pensionat.Properties.QueueProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.DeliverCallback;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.CachingConnectionFactoryConfigurer;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Component
@RequiredArgsConstructor
public class RoomEventApplication implements CommandLineRunner {

    @Autowired
    private QueueProperties queueProperties;
    @Override
    public void run(String... args) throws Exception {
        String queueName = queueProperties.getQueuename();
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueProperties.getHost());
        factory.setUsername(queueProperties.getUsername());
        factory.setPassword(queueProperties.getPassword());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            // https://www.baeldung.com/jackson-annotations#bd-jackson-polymorphic-type-handling-annotations
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }

}
