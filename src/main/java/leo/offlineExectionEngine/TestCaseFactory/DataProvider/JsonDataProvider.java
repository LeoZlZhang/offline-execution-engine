package leo.offlineExectionEngine.TestCaseFactory.DataProvider;

import com.vipabc.vliveshow.TestExecutionEngine.TestCase.TestCase;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FileSearchUtil;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.Filter;
import com.vipabc.vliveshow.TestExecutionEngine.Util.GsonUtil;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Evaluator;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Processor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class JsonDataProvider extends AbstractDataProvider implements Evaluator<Filter> {

    private File contentFolder;
    private Processor<String, String>[] processor;
    private List<File> jsonFileList;

    public JsonDataProvider(File folder) {
        this.contentFolder = folder;
    }

    public void setProcessor(Processor<String, String>... processor) {
        this.processor = processor;
    }

    @Override
    public boolean evaluate(Filter fileFilter) throws Exception {
        jsonFileList = FileSearchUtil.findAllFile(contentFolder, fileFilter);

        for (File jsonFile : jsonFileList) {
            if (!(FileUtils.readFileToString(jsonFile).isEmpty() || FileUtils.readFileToString(jsonFile).replace(" ", "").equals("[]")))
                return true;
        }

        return false;
    }

    @Override
    public List<TestCase> process(TestCase testCase) {
        if (jsonFileList == null)
            return null;

        List<TestCase> rtnTestCaseList = new ArrayList<>();
        for (File file : jsonFileList) {
            try {
                String str = FileUtils.readFileToString(file);
                if (processor != null)
                    for (Processor<String, String> worker : processor)
                        str = worker.process(str);
                TestCase[] tcsArray = GsonUtil.toObject(str, Array.newInstance(testCase.getClass(), 0).getClass());

                for (TestCase tc : tcsArray)
                    tc.setSourceFileName(file.getName());

                rtnTestCaseList.addAll(Arrays.asList(tcsArray));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtnTestCaseList;
    }
}
