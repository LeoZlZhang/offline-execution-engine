package worker;

import java.util.List;
import java.util.Map;

/**
 * Created by leo_zlzhang on 8/29/2016.
 * Pick profile from list cyclic
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ProfilePicker  {
    private int range;
    private List<Map<String, Object>> profileList;
    private CycleNumPicker cycleNumPicker = new CycleNumPicker();

    public ProfilePicker(List<Map<String, Object>> profileList, int range) {
        if(profileList == null || profileList.isEmpty())
            throw new RuntimeException("Empty profile list");
        if(range <=0  || range > profileList.size())
            throw new RuntimeException("Incorrect range for picker");
        this.profileList = profileList;
        this.range = range;
    }

    public Map<String, Object> next(){
        return profileList.get(cycleNumPicker.process(range));
    }

    public static ProfilePicker build(List<Map<String, Object>> profileFileList, int base){
        return new ProfilePicker(profileFileList, base);
    }
}
