package leo.webapplication.repository;

import leo.webapplication.model.EEProfile;
import org.springframework.stereotype.Repository;

/**
 * Created by leo_zlzhang on 10/24/2016.
 * Profile data
 */
@Repository
public class ProfileMongoRepository extends MongoRepository<EEProfile> {
    public ProfileMongoRepository() {
        super(EEProfile.class);
    }
}
