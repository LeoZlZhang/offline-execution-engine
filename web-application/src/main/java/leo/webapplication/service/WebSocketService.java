package leo.webapplication.service;

import leo.webapplication.model.ChannelMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    private Queue<ChannelMessage> messageQueue = new ConcurrentLinkedQueue<>();


    @Scheduled(fixedRate = 200)
    public void scheduledPublish() throws InterruptedException {
        ChannelMessage channelMessage = messageQueue.poll();
        while (channelMessage != null) {
            template.convertAndSend(String.format("/topic/%s", channelMessage.getChannel()), channelMessage.getMessage());
            channelMessage = messageQueue.poll();
            Thread.sleep(50);
        }

    }

    public void publishMessage(ChannelMessage channelMessage) {
        messageQueue.offer(channelMessage);
    }

}
