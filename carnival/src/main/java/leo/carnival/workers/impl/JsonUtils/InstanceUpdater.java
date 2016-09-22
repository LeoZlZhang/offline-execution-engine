package leo.carnival.workers.impl.JsonUtils;


import leo.carnival.workers.impl.GsonUtils;
import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.prototype.Setter.ProcessorSetter;

import java.util.Map;

@SuppressWarnings({"unchecked", "UnusedParameters", "Convert2Diamond"})
public class InstanceUpdater<T> implements Processor<T, T>, ProcessorSetter<MapValueUpdater> {


    private MapValueUpdater mapValueUpdater;

    @Override
    public T process(T targetInstance) {
        if (targetInstance == null)
            return null;

        String jsonString = GsonUtils.toJson(targetInstance);
        Map targetMap = GsonUtils.fromJsonObject(jsonString, Map.class);

        if (mapValueUpdater != null)
            mapValueUpdater.process(targetMap);

        jsonString = GsonUtils.toJson(targetMap);
        return GsonUtils.fromJsonObject(jsonString, (Class<T>) targetInstance.getClass());
    }


    @Override
    public T execute(T targetInstance) {
        return process(targetInstance);
    }


    @Override
    public InstanceUpdater<T> setWorker(MapValueUpdater worker) {
        this.mapValueUpdater = worker;
        return this;
    }


    public static <T> InstanceUpdater<T> build() {
        return new InstanceUpdater<T>();
    }

    public static <T> InstanceUpdater<T> build(MapValueUpdater mapValueUpdater) {
        return new InstanceUpdater<T>().setWorker(mapValueUpdater);
    }
}
