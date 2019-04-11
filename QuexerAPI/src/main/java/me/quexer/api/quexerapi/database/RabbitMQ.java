package me.quexer.api.quexerapi.database;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RabbitMQ {

    private Channel channel;

    public RabbitMQ(String queue) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://guest:oe196jUF@localhost");
            factory.setConnectionTimeout(10000);
            Connection conn = factory.newConnection();
            channel = conn.createChannel();

            channel.queueDeclare(queue, true, false, false, null);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String message, String queue) {
        try {
            channel.basicPublish("", queue, null, message.getBytes());
            System.out.println("Published Message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
