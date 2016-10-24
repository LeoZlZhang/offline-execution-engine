package leo.webapplication.model;

import leo.engineCore.engineFoundation.Gear.Gear;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by leo_zlzhang on 10/17/2016.
 * Gear model
 */
@SuppressWarnings("unused")
public class EEGear extends Gear implements Serializable{

    @Id
    private String id;

    public EEGear() {
    }

    public EEGear(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
