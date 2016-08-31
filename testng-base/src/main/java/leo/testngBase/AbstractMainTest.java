package leo.testngBase;

import leo.carnival.workers.implementation.GearicUtils.ArrayClone;
import leo.carnival.workers.implementation.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FolderEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.implementation.FileUtils.FileFilter;
import leo.carnival.workers.implementation.FileUtils.FolderFilter;
import leo.carnival.workers.implementation.JsonUtils.GsonUtils;
import leo.carnival.workers.implementation.ReflectUtils.ReflectMethodFilter;
import leo.carnival.workers.prototype.Evaluator;
import leo.carnival.workers.prototype.Processor;
import leo.engineCore.engineFoundation.Assert.TestResult;
import leo.engineCore.testEngine.TestEngine;
import leo.engineCore.worker.ProfilePicker;
import leo.engineData.DataProvider.JsonList2DataObjTransfer;
import leo.engineData.DataProvider.TestStrReplacer;
import leo.engineData.testData.TestData;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SynchronizationOnLocalVariableOrMethodParameter", "WeakerAccess", "DefaultAnnotationParam", "unchecked"})
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
    public void apiTest(TestData testData) {
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
    private ProfilePicker profilePicker() throws IOException {
        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.profileFolderName())));
        FileFilter fileFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build(".*\\.json")));
        AdvanceFileFilter advanceFileFilter = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(fileFilter);
        List<File> fileList = advanceFileFilter.process(new File(resourceFolderPath));
        List<Map<String, Object>> profileList = new ArrayList<>(fileList.size());
        for (File file : fileList)
            profileList.add(GsonUtils.fromJsonObject(file, Map.class));
        return ProfilePicker.build(profileList, testInfo.threadNumber());
    }


    @DataProvider(name = "DataSource", parallel = false)
    public Object[][] jsonDataSource() throws Exception {

        //get folder contains test data
        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.dataFolderName())));
        FileFilter debugDataFiler = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("^(?=.*?(?:debug)).+\\.(json|csv)")));
        FileFilter testDataFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("^(?!.*?(?:debug|gear)).+\\.(json|csv)").setWorker(RegexEvaluator.build(testInfo.testDataFilterRegex()))));

        List<File> debugData = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(debugDataFiler).process(new File(resourceFolderPath));
        List<File> testData = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(testDataFilter).process(new File(resourceFolderPath));


        //transfer test data files into string list, and inject with test data flow data
        FolderFilter flowFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build(testInfo.dataFlowFolderName())));
        TestStrReplacer replacer = TestStrReplacer.build(flowFilter, new File(resourceFolderPath));
        List<String> data = new ArrayList<>();
        for (File file : debugData.isEmpty() ? testData : debugData)
            data.add(replacer.process(FileUtils.readFileToString(file)));


        //test data in string -> test case objects
        ArrayClone clone = new ArrayClone().setCloneNum(testInfo.repeatTime());
        TestData[] bean = JsonList2DataObjTransfer.build(testInfo.testDataClass()).setCloneWorker(clone).process(data);

        return new ObjectBoxing().process(bean);
    }

    public class ObjectBoxing implements Processor<TestData[], Object[][]> {

        @Override
        public Object[][] process(TestData[] bean) {
            if (bean == null || bean.length == 0)
                return new Object[0][];

            Object[][] rtnObj = new Object[bean.length][];
            for (int i = 0, len = bean.length; i < len; i++)
                rtnObj[i] = new Object[]{bean[i]};
            return rtnObj;
        }

        @Override
        public Object[][] execute(TestData[] testDatas) {
            return process(testDatas);
        }
    }


}
