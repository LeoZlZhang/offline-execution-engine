package leo.engineCore.engineFoundation;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leozhang on 8/28/16.
 * Container of execution context
 */
@SuppressWarnings("unused")
public class ApplicationContext implements Serializable{
    private Logger logger;

    private Map<String, Object> context = new HashMap<>();
    private Map<String, Map.Entry<Method, Object>> methodRepo = new HashMap<>();
    private String gearName;
    private String flowName;
    private String stepName;


    public Map<String, Object> getContext() {
        return this.context;
    }

    public Map.Entry<Method, Object> getMethod(String methodName) {
        return methodRepo.get(methodName);
    }

    public Map<String, Map.Entry<Method, Object>> getMethodRepo() {
        return methodRepo;
    }

    public <T> ApplicationContext setMethodRepo(Class<T> cls) {
        if (cls == null)
            return this;

        try {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods)
                methodRepo.put(method.getName(), new AbstractMap.SimpleEntry<>(method, cls.newInstance()));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Fail to read methods from class: %s", cls.getName()), e);
        }
        return this;
    }


    public void setGearName(String gearName) {
        this.gearName = gearName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public void printExecutionInfo(){
        logger.debug(String.format("[%d][%s][%s][%s]...", Thread.currentThread().getId(), gearName, flowName, stepName));
    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
