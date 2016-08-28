package leo.carnival.workers.baseType.Setter;

import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Worker;

/**
 * Created by leozhang on 8/28/16.
 * Setter for processor
 */
public interface ProcessorSetter<T extends Processor> extends WorkerSetter<T, Worker> {
}
