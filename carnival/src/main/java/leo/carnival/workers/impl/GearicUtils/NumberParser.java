package leo.carnival.workers.impl.GearicUtils;

import leo.carnival.workers.prototype.Processor;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by leo_zlzhang on 9/22/2016.
 * parser number
 */
@SuppressWarnings("unused")
public class NumberParser implements Processor<Object, Number> {
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    public Number process(Object object) {
        if (object == null || !(object instanceof Number))
            return null;

        try {
            return numberFormat.parse(object.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Number execute(Object object) {
        return process(object);
    }
}
