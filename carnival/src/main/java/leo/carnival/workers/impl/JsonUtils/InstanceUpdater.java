package leo.carnival.workers.impl.JsonUtils;


import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.baseType.Setter.ProcessorSetter;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"unchecked", "UnusedParameters", "Convert2Diamond"})
public class InstanceUpdater<T> implements Processor<T, T>, ProcessorSetter<MapValueUpdater> {


    private MapValueUpdater mapValueUpdater;

    @Override
    public T process(T targetInstance) {
        if (targetInstance == null)
            return null;


        try {
            String jsonString = GsonUtils.toJson(targetInstance);
            Map targetMap = GsonUtils.fromJson(jsonString, Map.class)[0];

            if (mapValueUpdater != null)
                mapValueUpdater.process(targetMap);

            jsonString = GsonUtils.toJson(targetMap);
            return GsonUtils.fromJson(jsonString, (Class<T>) targetInstance.getClass())[0];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public T execute(T targetInstance) {
        return process(targetInstance);
    }


    @Override
    public InstanceUpdater<T> setWorker(MapValueUpdater worker) {
        if (worker == null)
            return this;

        this.mapValueUpdater = worker;
        return this;
    }


    public static <T> InstanceUpdater<T> build(Class<T> cls) {
        return new InstanceUpdater<T>();
    }

    public static <T> InstanceUpdater<T> build(Class<T> cls, MapValueUpdater mapValueUpdater) {
        return new InstanceUpdater<T>().setWorker(mapValueUpdater);
    }
}
