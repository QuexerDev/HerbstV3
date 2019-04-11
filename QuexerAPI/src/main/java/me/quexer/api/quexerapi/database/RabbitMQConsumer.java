package me.quexer.api.quexerapi.database;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;
import me.quexer.api.quexerapi.misc.AsyncTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitMQConsumer {

    private Channel channel;

    public RabbitMQConsumer(String queue) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://guest:oe196jUF@localhost");
            factory.setConnectionTimeout(10000);
            Connection conn = factory.newConnection();
            channel = conn.createChannel();
            channel.queueDeclare(queue, true, false, false, null);
            new AsyncTask(() -> {
                try {
                    channel.basicConsume(queue, false, queue + "-consumer", new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            // (process the message components here ...)
                            System.out.println("Message: " + new String(body, StandardCharsets.UTF_8));
                            channel.basicAck(deliveryTag, false);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
