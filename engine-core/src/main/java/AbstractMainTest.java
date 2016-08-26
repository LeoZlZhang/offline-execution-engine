import Engine.AbstractEngine;
import Engine.EngineImpl;
import leo.carnival.workers.filter.FileFilter;
import leo.carnival.workers.filter.FolderFilter;
import org.json.JSONException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("DefaultAnnotationParam")
public abstract class AbstractMainTest {
    protected AbstractEngine engine = new EngineImpl();

    private static String resourceFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\".replace("\\", File.separator);

    private TestInfo testInfo;
    protected ProfilePicker profilePicker;


    @BeforeSuite
    public void initialize() throws Exception {
        iniTestInfo();
        List<File> profileFolder = FolderFilter.build().setRegex(testInfo.profileFolderName()).process(new File(resourceFolderPath));
        List<File> profileFileList = FileFilter.build().setRegex(".*").process(profileFolder.get(0));
        profilePicker = new ProfilePicker(profileFileList, testInfo.threadNumber());

        List<File> gear = new FileFilter("gear\\.json").process(new File(resourceFolderPath));
        FileSearchUtil.findAllFile(resourceFolderPath, new FileFilter("gear\\.json"));
        engine.loadGear(gear.get(0));

        engine.execute("BeforeTestFlow");
    }


    private void iniTestInfo() throws IllegalAccessException, InstantiationException {
        Method method = ReflectUtil.findMethod(this.getClass(), method1 -> method1.getAnnotation(Test.class) != null && method1.getAnnotation(TestInfo.class) != null);
        testInfo = method != null ? method.getAnnotation(TestInfo.class) : null;
    }


    @AfterSuite
    public void finalization() {
        engine.execute("AfterTestFlow");
    }


    @DataProvider(name = "DataSource", parallel = false)
    public Object[][] jsonDataSource() throws Exception {

        File dataFolder = FileSearchUtil.findFirstFile(resourceFolderPath, new FolderFilter(testInfo.dataFolderName()));
        JsonDataProvider jdp = new JsonDataProvider(dataFolder);

        if (!jdp.evaluate(new FileFilter("^(?=.*?(?:debug)).+\\.(json|csv)")))
            jdp.evaluate(new AdvanceFileFilter(new FileFilter("^(?!.*?(?:debug|gear)).+\\.(json|csv)"), testInfo.testDataFilterRegex()));

        File dataFlowFolder = FileSearchUtil.findFirstFile(resourceFolderPath, new FolderFilter(testInfo.dataFlowFolderName()));
        jdp.setProcessor(new StringInjector(dataFlowFolder));

        List<TestCase> tcList = null;
        try {
            TestCaseFactoryImpl tcFactory = new TestCaseFactoryImpl();
            tcFactory.setDataProvider(jdp);
            tcFactory.setResultCloner(new ListCloner(testInfo.repeatTime()));
            tcList = tcFactory.process(testInfo.testCaseClass().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataProviderBoxing(tcList);
    }


    private <T> Object[][] dataProviderBoxing(Collection<T> tcs) {
        Object[][] rtnObj = new Object[tcs.size()][];
        Iterator<T> iter = tcs.iterator();
        for (int index = 0, len = tcs.size(); index < len; index++)
            rtnObj[index] = new Object[]{iter.next()};
        return rtnObj;
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
            Map rtnMap = GsonUtil.toObject(file, Map.class);
            rtnMap.put("ResourcesPath", resourceFolderPath);
            return rtnMap;
        }
    }

}
