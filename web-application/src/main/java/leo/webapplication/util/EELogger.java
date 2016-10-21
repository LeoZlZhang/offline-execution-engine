package leo.webapplication.util;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by leo_zlzhang on 10/19/2016.
 * Custom logger, support log to web socket
 */
@SuppressWarnings({"unchecked", "WeakerAccess", "unused"})
public class EELogger extends Logger {

    private ExecutionLogPublisher publisher;

    protected EELogger(String name) {
        super(name);
    }


    public static EELogger getLogger(Class cls) {
        return new EELogger(cls.getName());
    }


    @Override
    public void info(Object message) {
        super.info(message);
        if (publisher != null)
            publisher.execute(
                    message instanceof Map ?
                            message :
                            SocketMessage.build("black", message));
    }

    @Override
    public void error(Object message) {
        super.error(message);
        if (publisher != null)
            publisher.execute(
                    message instanceof Map ?
                            message :
                            SocketMessage.build("red", message));
    }


    @Override
    public void warn(Object message) {
        super.warn(message);
        if (publisher != null)
            publisher.execute(
                    message instanceof Map ?
                            message :
                            SocketMessage.build("#d58512", message));
    }


    /**
     * Getter
     */
    public ExecutionLogPublisher getWorker() {
        return publisher;
    }


    /**
     * Setter
     */
    public EELogger setWorker(ExecutionLogPublisher publisher) {
        this.publisher = publisher;
        return this;
    }
}
