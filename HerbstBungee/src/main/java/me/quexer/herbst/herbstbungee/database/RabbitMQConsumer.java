package me.quexer.herbst.herbstbungee.database;

import com.rabbitmq.client.*;
import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.AsyncTask;
import me.quexer.herbst.herbstbungee.obj.BackendGroup;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.acl.Group;
import java.util.UUID;
import java.util.stream.Collectors;

public class RabbitMQConsumer {

    private Channel channel;

    private HerbstBungee plugin;



    public RabbitMQConsumer(String queue, HerbstBungee plugin) {

        this.plugin = plugin;

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
                            String delivery = new String(body, StandardCharsets.UTF_8);
                            String[] message = delivery.split(";");
                            String type = message[0];
                            String senderUUID = message[1];
                            String targetUUID = message[2];
                            if (type == "friend-request") {
                                plugin.getFriendManager().sendRequest(senderUUID, targetUUID);
                            } else if (type == "party-invite") {

                            } else if (type == "friend-jump") {
                                plugin.getFriendManager().jump(senderUUID, targetUUID);
                            } else if (type == "friend-toggle") {
                                plugin.getFriendManager().changeSetting(senderUUID, targetUUID);
                            } else if (type == "friend-remove") {
                                plugin.getFriendManager().removeFriend(senderUUID, targetUUID);
                            }/* else if (type == "kick") {
                                UUID id = UUID.fromString(targetUUID);
                                BackendGroup group = plugin.getBackendManager().getGroups().stream().filter(backendGroup -> backendGroup.getLevelID() == Integer.valueOf(senderUUID)).collect(Collectors.toList()).get(0);
                                if (plugin.getProxy().getPlayer(id) != null) {
                                    plugin.getProxy().getPlayer(id).disconnect(
                                            "§8§m-----------------------------------------------------\n" +
                                                    "§7Du hast die Gruppe " + group.getFullName() + " §7erhalten!\n" +
                                                    "§7Bitte betrete den Server erneut§8.\n" +
                                                    "§8§m-----------------------------------------------------");
                                }

                            }
                            */
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
