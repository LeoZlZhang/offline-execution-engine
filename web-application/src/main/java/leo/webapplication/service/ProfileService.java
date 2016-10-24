package leo.webapplication.service;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.model.EEProfile;
import leo.webapplication.repository.ProfileMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by leo_zlzhang on 10/24/2016.
 * Profile service
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class ProfileService {

    @Autowired
    ProfileMongoRepository profileMongoRepository;

    public List<EEProfile> loadProfile(EEProfile profile){
        Map kvMap = JacksonUtils.fromObject2Map(profile);
        Query query = new Query();
        for (Object key : kvMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(kvMap.get(key)));

        return profileMongoRepository.find(query);
    }

    public boolean saveProfile(EEProfile profile){
        profileMongoRepository.save(profile);
        return true;
    }

    public Object[] deleteProfile(EEProfile profile){
        Map kvMap = JacksonUtils.fromObject2Map(profile);
        Query query = new Query();
        for(Object key : kvMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(kvMap.get(key)));

        List<EEProfile> found = profileMongoRepository.find(query);

        switch (found.size()) {
            case 0:
                return new Object[]{false, "found no profile to delete"};
            case 1:
                profileMongoRepository.remove(query);
                return new Object[]{true, found.get(0)};
            default:
                return new Object[]{false, "found more than one profile to delete"};
        }
    }
}
