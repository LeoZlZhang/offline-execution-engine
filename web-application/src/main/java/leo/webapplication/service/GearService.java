package leo.webapplication.service;

import leo.webapplication.model.EEGear;
import leo.webapplication.repository.GearMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by leo_zlzhang on 10/17/2016.
 * Gear service
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class GearService {

    @Autowired
    GearMongoRepository gearMongoRepository;

    public EEGear getOneGear(){
        return gearMongoRepository.findOne(new Query());
    }


}
