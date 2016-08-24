import com.vipabc.vliveshow.TestExecutionEngine.Engine.AbstractEngine;
import com.vipabc.vliveshow.TestExecutionEngine.Engine.EngineImpl;
import com.vipabc.vliveshow.TestExecutionEngine.Engine.util.ReflectUtil;
import com.vipabc.vliveshow.TestExecutionEngine.TestCase.TestCase;
import com.vipabc.vliveshow.TestExecutionEngine.TestCaseFactory.DataProvider.JsonDataProvider;
import com.vipabc.vliveshow.TestExecutionEngine.TestCaseFactory.DataProvider.StringInjector;
import com.vipabc.vliveshow.TestExecutionEngine.TestCaseFactory.TestCaseFactoryImpl;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.AdvanceFileFilter;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FileFilter;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FileSearchUtil;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FolderFilter;
import com.vipabc.vliveshow.TestExecutionEngine.Util.GsonUtil;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.ListCloner;
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


        List<File> profileFileList = FileSearchUtil.findAllFile(resourceFolderPath, new FileFilter(testInfo.profileFolderName()));
        profilePicker = new ProfilePicker(profileFileList, testInfo.threadNumber());

        List<File> gear = FileSearchUtil.findAllFile(resourceFolderPath, new FileFilter("gear\\.json"));
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
        }catch (Exception e){
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
