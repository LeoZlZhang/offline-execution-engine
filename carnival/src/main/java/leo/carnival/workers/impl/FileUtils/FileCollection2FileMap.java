package leo.carnival.workers.impl.FileUtils;


import leo.carnival.workers.prototype.Processor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo_zlzhang on 8/31/2016.
 * Load collection of files to Map<File, file content in string>
 */
public class FileCollection2FileMap implements Processor<Collection<File>, Map<File, String>> {
    @Override
    public Map<File, String> process(Collection<File> files) {
        Map<File, String> rtnMap = new HashMap<>();
        for (File file : files) {
            try {
                rtnMap.put(file, FileUtils.readFileToString(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtnMap;
    }

    @Override
    public Map<File, String> execute(Collection<File> files) {
        return null;
    }
}
