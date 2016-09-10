package leo.webapplication.model;

import com.vipabc.vliveshow.apitest.bean.APITestData;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by leozhang on 9/11/16.
 * ApiData
 */
@SuppressWarnings("unused")
public class ApiData extends APITestData implements Serializable {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
