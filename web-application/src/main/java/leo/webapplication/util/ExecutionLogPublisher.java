package leo.webapplication.util;

import leo.carnival.workers.prototype.Executor;
import leo.webapplication.model.ChannelMessage;
import leo.webapplication.service.WebSocketService;

/**
 * Created by leo_zlzhang on 10/19/2016.
 * Publish execution log
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiredMembersInspection"})
public class ExecutionLogPublisher implements Executor<Object, Object>{

    private WebSocketService webSocketService;

    private String channel;

    private ExecutionLogPublisher(String channel, WebSocketService webSocketService) {
        this.channel = channel;
        this.webSocketService = webSocketService;
    }

    public static ExecutionLogPublisher build(String channel, WebSocketService webSocketService){
        return new ExecutionLogPublisher(channel, webSocketService);
    }

    @Override
    public Object execute(Object message) {
        webSocketService.publishMessage(ChannelMessage.build(channel, message));

        return null;
    }
}
