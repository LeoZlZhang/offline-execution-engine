package leo.webapplication.util;

/**
 * Created by leo_zlzhang on 10/21/2016.
 * Message model for web socket
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SocketMessage {
    private String color = "black";
    private Object message = "";

    private SocketMessage(String color, Object message){
        this.color = color;
        this.message = message;
    }

    private SocketMessage(Object message){
        this.message = message;
    }

    public static SocketMessage build(String color, Object message){
        return new SocketMessage(color, message);
    }

    public static SocketMessage build(Object message){
        return new SocketMessage(message);
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }



}
