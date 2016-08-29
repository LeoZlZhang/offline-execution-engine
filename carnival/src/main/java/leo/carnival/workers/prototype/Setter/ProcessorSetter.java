package leo.carnival.workers.prototype.Setter;

import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.prototype.Worker;

/**
 * Created by leozhang on 8/28/16.
 * Setter for processor
 */
public interface ProcessorSetter<T extends Processor> extends WorkerSetter<T, Worker> {
}
