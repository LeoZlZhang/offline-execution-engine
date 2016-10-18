package leo.webapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;


//    @Scheduled(fixedRate = 500)
    public void trigger() {
        Date date = new Date();
        System.out.println(date);
        this.template.convertAndSend("/topic/greetings", "Date: " + date );
    }



}
