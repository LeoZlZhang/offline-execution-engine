package TestCaseFactory.DataProvider;

import com.vipabc.vliveshow.TestExecutionEngine.TestCase.TestCase;
import com.vipabc.vliveshow.TestExecutionEngine.Util.CSVFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "MismatchedQueryAndUpdateOfCollection"})
public class CSVDataProvider extends AbstractDataProvider {

    private List<File> sourceFileList;


    @Override
    public List<TestCase> process(TestCase testCase) {
        if(sourceFileList==null)
            return null;

        List<TestCase> rtnList = new ArrayList<>();
        for (File file : sourceFileList) {
            try {
                CSVFileUtil csv = new CSVFileUtil(file);
                List<String> titles = csv.readLineToArray();
                List<List<String>> lines = csv.readAll();

                for (List<String> line : lines) {
                    assert line.size() == titles.size();

                    TestCase tc = testCase.getInstance();

                    for (int columnIndex = 0, len = titles.size(); columnIndex < len; columnIndex++) {
                        String title = titles.get(columnIndex);
                        String cell = line.get(columnIndex);
                        if (!tc.validate(title, cell))
                            throw new Exception(String.format(
                                    "Test data validate fail: File:%s, Line name:%s, Column: %s", file.getName(), line.get(0), title));
                    }
                    tc.setSourceFileName(file.getName());
                    rtnList.add(tc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rtnList;
    }
}
