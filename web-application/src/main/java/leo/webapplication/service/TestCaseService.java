package leo.webapplication.service;

import leo.carnival.workers.impl.JacksonUtils;
import leo.webapplication.model.ApiData;
import leo.webapplication.repository.ApiTestMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by leo_zlzhang on 10/13/2016.
 * Test case service
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class TestCaseService {

    @Autowired
    ApiTestMongoRepository apiTestMongoRepository;

    public List<ApiData> getApiData(ApiData apiData) {
        Map apiDataMap = JacksonUtils.fromObject2Map(apiData);
        Query query = new Query();
        for (Object key : apiDataMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(apiDataMap.get(key)));

        return apiTestMongoRepository.find(query);
    }

    public boolean saveApiData(ApiData data) {
        apiTestMongoRepository.save(data);
        return true;
    }

    public Object[] deleteApiData(ApiData apiData) {
        Map apiDataMap = JacksonUtils.fromObject2Map(apiData);
        Query query = new Query();
        for (Object key : apiDataMap.keySet())
            query.addCriteria(Criteria.where(key.toString()).is(apiDataMap.get(key)));

        List<ApiData> found = apiTestMongoRepository.find(query);

        switch (found.size()) {
            case 0:
                return new Object[]{false, "found no case to delete"};
            case 1:
                apiTestMongoRepository.remove(query);
                return new Object[]{true, found.get(0)};
            default:
                return new Object[]{false, "found more than one case to delete"};
        }
    }


}
