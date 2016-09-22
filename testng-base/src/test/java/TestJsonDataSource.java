import leo.carnival.workers.impl.Evaluators;
import leo.carnival.workers.impl.FileUtils.FileFilter;
import leo.carnival.workers.impl.FileUtils.FolderFilter;
import leo.carnival.workers.impl.Processors;
import leo.engineData.DataProvider.ContentReplacer;
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
        FolderFilter dataFlowFolderFilter = Processors.FolderFilter(Evaluators.FolderEvaluator(Evaluators.RegexEvaluator("DataFlow")));

        ContentReplacer contentReplacer = ContentReplacer.build(dataFlowFolderFilter.process(sourceFolder).get(0));

        List<File> fileList = Processors.FileFilter(Evaluators.FileEvaluator(Evaluators.RegexEvaluator("Poc4Flow.json"))).process(sourceFolder);

        System.out.println(contentReplacer.process(FileUtils.readFileToString(fileList.get(0))));
    }

    @Test
    public void testJsonProvider(){
        File sourceFolder = new File(System.getProperty("user.dir") + "\\src\\main\\resources");
        //get folder contains test data
        FolderFilter folderFilter = Processors.FolderFilter(Evaluators.FolderEvaluator(Evaluators.RegexEvaluator("Data")));
        FileFilter testDataFilter = Processors.FileFilter(Evaluators.FileEvaluator(Evaluators.RegexEvaluator("^(?!.*?(?:debug|gear)).+\\.(json|csv)").setRegexEvaluator(Evaluators.RegexEvaluator(".*\\.json"))));

        List<File> testDataFileList = Processors.FileFilterAdvance(folderFilter,testDataFilter).process(sourceFolder);

        System.out.println(testDataFileList.size());
        System.out.println(testDataFileList);

    }
}
