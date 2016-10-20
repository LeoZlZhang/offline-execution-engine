package leo.engineData.logging;

import leo.carnival.workers.prototype.Worker;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by leo_zlzhang on 10/19/2016.
 * Custom logger, support log to web socket
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class EELogger extends Logger{

    private Worker worker;

    protected EELogger(String name) {
        super(name);
    }


    public static EELogger getLogger(Class cls){
        return new EELogger(cls.getName());
    }



    public void info(Object message) {
        super.info(message);
        if (worker != null)
            worker.execute(message);
    }

    public void info(Object message, Throwable e) {
        super.info(message, e);
        if (worker != null)
            worker.execute(String.format("%s\r\n%s", message, printStackTrace(e)));
    }

    public void error(Object message) {
        super.error(message);
    }

    public void error(Object message, Throwable e) {
        super.error(message, e);
        if (worker != null)
            worker.execute(String.format("%s\r\n%s", message, printStackTrace(e)));
    }


    private String printStackTrace(Throwable e) {
        String result = "";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            result = sw.getBuffer().toString();
            pw.close();
            sw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    /**
     * Getter
     */
    public Worker getWorker() {
        return worker;
    }


    /**
     * Setter
     * @return
     */
    public EELogger setWorker(Worker worker) {
        this.worker = worker;
        return this;
    }
}
