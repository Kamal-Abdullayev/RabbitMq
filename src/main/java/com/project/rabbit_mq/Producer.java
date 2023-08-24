package com.project.rabbit_mq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.sql.Timestamp;

public class Producer {
    private final static String QUEUE_NAME = "message_queue";

    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 1; i <= 100000; i++) {
                String message = "Message " + new Timestamp(System.currentTimeMillis());
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Sent: " + message);
            }
        }
        Long end = System.currentTimeMillis();

        System.out.println(end-start);
    }
}
