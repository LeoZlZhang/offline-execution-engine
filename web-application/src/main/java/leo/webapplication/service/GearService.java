package leo.webapplication.service;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.model.EEGear;
import leo.webapplication.repository.GearMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by leo_zlzhang on 10/17/2016.
 * Gear service
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "unused"})
@Service
public class GearService {

    @Autowired
    GearMongoRepository gearMongoRepository;

    public List<EEGear> loadGear(EEGear gear ){
        Map kvMap = JacksonUtils.fromObject2Map(gear);
        Query query = new Query();
        for (Object key : kvMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(kvMap.get(key)));

        return gearMongoRepository.find(query);
    }

    public boolean saveGear(EEGear gear) {
        gearMongoRepository.save(gear);
        return true;
    }

    public Object[] deleteGear(EEGear gear) {
        Map kvMap = JacksonUtils.fromObject2Map(gear);
        Query query = new Query();
        for (Object key : kvMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(kvMap.get(key)));

        List<EEGear> found = gearMongoRepository.find(query);

        switch (found.size()) {
            case 0:
                return new Object[]{false, "found no gear to delete"};
            case 1:
                gearMongoRepository.remove(query);
                return new Object[]{true, found.get(0)};
            default:
                return new Object[]{false, "found more than one gear to delete"};
        }
    }
}
