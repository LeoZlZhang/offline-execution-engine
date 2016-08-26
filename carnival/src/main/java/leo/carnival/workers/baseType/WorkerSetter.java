package leo.carnival.workers.baseType;

/**
 * Created by leo_zlzhang on 8/26/2016.
 * interface to set worker
 */
public interface WorkerSetter<T extends  Worker>{
    void setWorker(T worker);
}
