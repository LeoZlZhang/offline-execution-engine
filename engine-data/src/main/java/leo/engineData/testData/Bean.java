package leo.engineData.testData;

import leo.carnival.workers.prototype.Worker;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by leo_zlzhang on 10/19/2016.
 * Base element of data
 */
abstract public class Bean<T, G> implements Worker<T, G>, Serializable{

    protected Logger logger;


    /**
     * Setter
     */
    public Bean<T, G> setCustomLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

}
