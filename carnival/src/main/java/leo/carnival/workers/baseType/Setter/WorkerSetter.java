package leo.carnival.workers.baseType.Setter;

import leo.carnival.workers.baseType.Worker;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * interface to set worker
 */
public interface WorkerSetter<T extends Worker, G extends Worker> {
    G setWorker(T worker);
}
