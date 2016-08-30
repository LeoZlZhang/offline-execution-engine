import leo.carnival.workers.implementation.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FolderEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.implementation.FileUtils.FileFilter;
import leo.carnival.workers.implementation.FileUtils.FolderFilter;
import leo.engineData.DataProvider.TestStrReplacer;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by leo_zlzhang on 8/30/2016.
 * ...
 */
public class TestJsonDataSource {


    @Test
    public void testStringInject() throws IOException {
        File sourceFolder = new File(System.getProperty("user.dir") + "\\src\\main\\resources");
        //transfer test data files into string list, and inject with test data flow data
        FolderFilter dataFlowFolderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build("DataFlow")));
        TestStrReplacer testStrReplacer = TestStrReplacer.build(dataFlowFolderFilter, sourceFolder);
        List<File> fileList = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("Poc4Flow.json"))).process(sourceFolder);
        System.out.println(testStrReplacer.process(FileUtils.readFileToString(fileList.get(0))));
    }

    @Test
    public void testJsonProvider(){
        File sourceFolder = new File(System.getProperty("user.dir") + "\\src\\main\\resources");
        //get folder contains test data
        FolderFilter folderFilter = FolderFilter.build(FolderEvaluator.build(RegexEvaluator.build("Data")));
        FileFilter testDataFilter = FileFilter.build(FileEvaluator.build(RegexEvaluator.build("^(?!.*?(?:debug|gear)).+\\.(json|csv)").setWorker(RegexEvaluator.build(".*\\.json"))));

        List<File> testDataFileList = AdvanceFileFilter.build().setWorker(folderFilter).setWorker(testDataFilter).process(sourceFolder);

        System.out.println(testDataFileList.size());
        System.out.println(testDataFileList);

    }
}
