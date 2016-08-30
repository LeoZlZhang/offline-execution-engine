import leo.carnival.workers.implementation.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FolderEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.implementation.FileUtils.FileFilter;
import leo.carnival.workers.implementation.FileUtils.FolderFilter;
import leo.carnival.workers.implementation.JsonUtils.GsonUtils;
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

        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build("Profile")));
        FileFilter fileFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build(".*\\.json")));
        AdvanceFileFilter advanceFileFilter = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(fileFilter);

        List<File> fileList = advanceFileFilter.process(new File(System.getProperty("user.dir") + "\\src\\main\\resources"));

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
