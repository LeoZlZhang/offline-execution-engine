package leo.webapplication.model;

import org.springframework.data.annotation.Id;

import java.util.Map;

/**
 * Created by leo_zlzhang on 10/24/2016.
 * Profile model
 */
@SuppressWarnings("unused")
public class EEProfile {

    @Id
    private String id;

    private String name;

    private Map<String, Object> profile;

    public EEProfile() {
    }

    public EEProfile(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getProfile() {
        return profile;
    }

    public void setProfile(Map<String, Object> profile) {
        this.profile = profile;
    }
}
