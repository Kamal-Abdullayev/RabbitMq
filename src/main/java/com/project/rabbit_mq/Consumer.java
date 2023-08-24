package com.project.rabbit_mq;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Consumer {
    private final static String QUEUE_NAME = "message_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Received: " + message);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            System.out.println("Consumer started. Waiting for messages...");

            Thread.sleep(10000);
        }
    }
}
