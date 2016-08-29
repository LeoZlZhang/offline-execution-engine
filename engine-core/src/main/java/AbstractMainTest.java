import engineFoundation.Assert.TestResult;
import leo.carnival.workers.implementation.JsonUtils.GsonUtils;
import testData.TestData;
import testEngine.TestEngine;
import worker.DataProvider.TestDataInjector;
import worker.DataProvider.TestCaseGenerator;
import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.implementation.ArrayCloner;
import leo.carnival.workers.implementation.CollectionUtils.FirstElementPicker;
import leo.carnival.workers.prototype.Evaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FolderEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.implementation.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.implementation.FileUtils.FileFilter;
import leo.carnival.workers.implementation.FileUtils.FolderFilter;
import leo.carnival.workers.implementation.ReflectUtils.ReflectMethodFilter;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import worker.ProfilePicker;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings({"SynchronizationOnLocalVariableOrMethodParameter", "WeakerAccess", "DefaultAnnotationParam"})
public abstract class AbstractMainTest {
    protected TestEngine engine = new TestEngine();
    private static String resourceFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\".replace("\\", File.separator);
    private TestInfo testInfo;
    protected ProfilePicker profilePicker;


    @BeforeSuite
    public void initialize() throws Exception {
        this.testInfo = testInfo();
        this.profilePicker = profilePicker();

        List<File> gearFile = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("gear\\.json"))).process(new File(resourceFolderPath));
        engine.loadGearFromFile(gearFile.get(0));
    }

    @TestInfo(testDataClass = TestData.class,
            threadNumber = 1,
            repeatTime = 0,
            profileFolderName = "MosaiEnvProfile",
            dataFolderName = "Data",
            dataFlowFolderName = "DataFlow",
            testDataFilterRegex = "3_getUserBaseInfo.json")
    @Test(dataProvider = "DataSource")
    public void apiTest(TestData testData) throws Exception {
        Map<String, Object> profile = profilePicker.next();
        synchronized (profile) {
            TestResult result = engine.execute(testData.update(profile));
            result.Assert();
        }
    }

    @AfterSuite
    public void finalization() {
    }

    private TestInfo testInfo() throws IllegalAccessException, InstantiationException {
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

    @SuppressWarnings("unchecked")
    private ProfilePicker profilePicker() throws Exception {
        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.profileFolderName())));
        FileFilter fileFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build(".*\\.json")));
        AdvanceFileFilter advanceFileFilter = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(fileFilter);
        List<File> fileList = advanceFileFilter.process(new File(resourceFolderPath));
        List<Map<String, Object>> profileList = new ArrayList<>(fileList.size());
        for(File file : fileList)
            profileList.add(GsonUtils.fromJsonObject(file, Map.class));
        return ProfilePicker.build(profileList, testInfo.threadNumber());
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
        TestData[] testDatas = TestCaseGenerator.build(testInfo.testDataClass()).process(testDataStrings);
        if (testDatas == null || testDatas.length == 0) {
            return new Object[0][];
        }


        //Clone test case if need
        testDatas = new ArrayCloner<TestData>().setCloneNum(testInfo.repeatTime()).process(testDatas);

        return new ObjectBoxing().process(testDatas);
    }

    public class ObjectBoxing implements Processor<TestData[], Object[][]> {

        @Override
        public Object[][] process(TestData[] testDatas) {
            Object[][] rtnObj = new Object[testDatas.length][];
            for (int i = 0, len = testDatas.length; i < len; i++)
                rtnObj[i] = new Object[]{testDatas[i]};
            return rtnObj;
        }

        @Override
        public Object[][] execute(TestData[] testDatas) {
            return process(testDatas);
        }
    }


}
