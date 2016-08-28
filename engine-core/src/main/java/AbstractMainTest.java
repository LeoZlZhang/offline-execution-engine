import engine.AbstractEngine;
import engine.EngineImpl;
import testCase.TestCase;
import worker.DataProvider.TestDataInjector;
import worker.DataProvider.TestCaseGenerator;
import leo.carnival.workers.baseType.Processor;
import leo.carnival.workers.impl.ArrayCloner;
import leo.carnival.workers.impl.CollectionUtils.FirstElementPicker;
import leo.carnival.workers.baseType.Evaluator;
import leo.carnival.workers.impl.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.impl.FileUtils.Evaluator.FolderEvaluator;
import leo.carnival.workers.impl.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.impl.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.impl.FileUtils.FileFilter;
import leo.carnival.workers.impl.FileUtils.FolderFilter;
import leo.carnival.workers.impl.JsonUtils.GsonUtils;
import leo.carnival.workers.impl.ReflectUtils.ReflectMethodFilter;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractMainTest {
    protected AbstractEngine engine = new EngineImpl();

    private static String resourceFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\".replace("\\", File.separator);

    private TestInfo testInfo;
    protected ProfilePicker profilePicker;


    @BeforeSuite
    public void initialize() throws Exception {
        this.testInfo = searchFistTestInfo();
        this.profilePicker = initialProfilePicker();

        List<File> gear = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("gear\\.json"))).process(new File(resourceFolderPath));
        engine.loadGear(gear.get(0));
    }


    private TestInfo searchFistTestInfo() throws IllegalAccessException, InstantiationException {
        Method method = ReflectMethodFilter.build(new Evaluator<Method>() {
            @Override
            public boolean evaluate(Method method) {
                return method != null && method.getAnnotation(Test.class) != null && method.getAnnotation(TestInfo.class) != null;
            }

            @Override
            public Boolean execute(Method method) {
                return evaluate(method);
            }
        }).process(this);
        return method != null ? method.getAnnotation(TestInfo.class) : null;
    }

    private ProfilePicker initialProfilePicker() throws Exception {
        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.profileFolderName())));
        FileFilter fileFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build(".*\\.json")));
        AdvanceFileFilter advanceFileFilter = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(fileFilter);
        List<File> profileList = advanceFileFilter.process(new File(resourceFolderPath));
        return profileList == null || profileList.size() == 0 ? null : new ProfilePicker(profileList, testInfo.threadNumber());

    }


    @AfterSuite
    public void finalization() {
    }


    @DataProvider(name = "DataSource", parallel = false)
    public Object[][] jsonDataSource() throws Exception {

        //get folder contains test data
        List<File> dataFolders = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.dataFolderName()))).process(new File(resourceFolderPath));
        File dataFolder = new FirstElementPicker<File>().process(dataFolders);


        //get test data files
        List<File> testDataFiles = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("^(?=.*?(?:debug)).+\\.(json|csv)"))).process(dataFolder);
        testDataFiles = testDataFiles == null || testDataFiles.isEmpty() ?
                FileFilter.build(FileEvaluator.build(RegexEvaluator.build("^(?!.*?(?:debug|gear)).+\\.(json|csv)"))).process(dataFolder) :
                testDataFiles;
        if (testDataFiles == null || testDataFiles.isEmpty())
            return new Object[0][];


        //get test data flow files
        List<File> dataFlowFolders = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.dataFlowFolderName()))).process(new File(resourceFolderPath));
        File dataFlowFolder = new FirstElementPicker<File>().process(dataFlowFolders);


        //transfer test data files into string list, and inject with test data flow data
        List<String> testDataStrings = new ArrayList<>(testDataFiles.size());
        for (File file : testDataFiles)
            testDataStrings.add(TestDataInjector.build(dataFlowFolder).process(FileUtils.readFileToString(file)));


        //test data in string -> test case objects
        TestCase[] testCases = TestCaseGenerator.build(testInfo.testCaseClass()).process(testDataStrings);
        if (testCases == null || testCases.length == 0) {
            return new Object[0][];
        }


        //Clone test case if need
        testCases = new ArrayCloner<TestCase>().setCloneNum(testInfo.repeatTime()).process(testCases);

        return new ObjectBoxing().process(testCases);
    }

    public class ObjectBoxing implements Processor<TestCase[], Object[][]> {

        @Override
        public Object[][] process(TestCase[] testCases) {
            Object[][] rtnObj = new Object[testCases.length][];
            for (int i = 0, len = testCases.length; i < len; i++)
                rtnObj[i] = new Object[]{testCases[i]};
            return rtnObj;
        }

        @Override
        public Object[][] execute(TestCase[] testCases) {
            return process(testCases);
        }
    }


    @SuppressWarnings("WeakerAccess")
    public class ProfilePicker {
        private int index = 0;
        private int factor = 1;
        private List<Map<String, Object>> profileList = new ArrayList<>();

        ProfilePicker(List<File> profileFileList, int base) throws IOException, JSONException {
            for (File file : profileFileList)
                profileList.add(convertProfile(file));
            assert profileList.size() > 0;
            this.factor = Math.min(base > 0 ? base : 1, profileList.size());
        }

        public Map<String, Object> next() {
            int pos = index % factor;
            index++;
            return profileList.get(pos);
        }

        @SuppressWarnings("unchecked")
        private Map<String, Object> convertProfile(File file) throws IOException {
            Map rtnMap = GsonUtils.fromJson(file, Map.class)[0];
            rtnMap.put("ResourcesPath", resourceFolderPath);
            return rtnMap;
        }
    }

}
