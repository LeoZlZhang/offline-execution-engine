package leo.webapplication.service;

import leo.webapplication.model.ApiData;
import leo.webapplication.repository.ApiTestMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by leo_zlzhang on 10/13/2016.
 * Test case service
 */
@Service
public class TestCaseService {

    @Autowired
    ApiTestMongoRepository apiTestMongoRepository;



    public List<ApiData> getApiDataByName(String name) {
        return apiTestMongoRepository.find(new Query(Criteria.where("name").is(name)));
    }


    public List<ApiData> getApiDataBySourceFileName(String sourceFileName) {
        return apiTestMongoRepository.find(new Query(Criteria.where("sourceFileName").is(sourceFileName)));
    }

    public List<ApiData> getApiDataOfAll(){
        return apiTestMongoRepository.findAll();
    }


    public boolean saveApiData(ApiData data){
        deleteApiDataBySourceFileName(data.getSourceFileName());
        apiTestMongoRepository.save(data);
        return true;
    }

    public boolean deleteApiDataBySourceFileName(String sourceFileName) {
        apiTestMongoRepository.remove(new Query(Criteria.where("sourceFileName").is(sourceFileName)));
        return true;
    }
}
