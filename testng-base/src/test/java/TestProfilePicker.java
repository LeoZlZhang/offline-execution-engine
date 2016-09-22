import leo.carnival.workers.impl.Evaluators;
import leo.carnival.workers.impl.FileUtils.FileFilter;
import leo.carnival.workers.impl.FileUtils.FileFilterAdvance;
import leo.carnival.workers.impl.FileUtils.FolderFilter;
import leo.carnival.workers.impl.GsonUtils;
import leo.carnival.workers.impl.Processors;
import leo.engineCore.worker.ProfilePicker;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leo_zlzhang on 8/30/2016.
 * ...
 */
public class TestProfilePicker {



    @SuppressWarnings("unchecked")
    @Test
    public void testPicker() throws IOException {

        FolderFilter folderFilter = Processors.FolderFilter(Evaluators.FolderEvaluator(Evaluators.RegexEvaluator("Profile")));
        FileFilter fileFilter = Processors.FileFilter(Evaluators.FileEvaluator(Evaluators.RegexEvaluator(".*\\.json")));
        FileFilterAdvance fileFilterAdvance = Processors.FileFilterAdvance(folderFilter, fileFilter);

        List<File> fileList = fileFilterAdvance.process(new File(System.getProperty("user.dir") + "\\src\\main\\resources"));

        List<Map<String, Object>> profileList = new ArrayList<>(fileList.size());
        for (File file : fileList)
            profileList.add(GsonUtils.fromJsonObject(file, Map.class));

        ProfilePicker profilePicker = ProfilePicker.build(profileList, 5);
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
        System.out.println(profilePicker.next());
    }
}
