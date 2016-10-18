package leo.webapplication.repository;

import leo.webapplication.model.EEGear;
import org.springframework.stereotype.Repository;

/**
 * Created by leo_zlzhang on 10/17/2016.
 * Gear repository
 */
@Repository
public class GearMongoRepository extends MongoRepository<EEGear> {
    public GearMongoRepository() {
        super(EEGear.class);
    }
}
