package leo.webapplication.model;

/**
 * Created by leo_zlzhang on 10/19/2016.
 * Message object to published to client by websocket
 * Contain a channel name and a message object
 */
@SuppressWarnings("WeakerAccess")
public class ChannelMessage {

    private String channel;
    private Object message;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static ChannelMessage build(String channel, Object message){
        ChannelMessage channelMessage = new ChannelMessage();
        channelMessage.setChannel(channel);
        channelMessage.setMessage(message);
        return channelMessage;
    }

}
