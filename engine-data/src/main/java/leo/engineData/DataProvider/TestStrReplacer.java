package leo.engineData.DataProvider;


import leo.carnival.workers.implementation.FileUtils.AdvanceFileFilter;
import leo.carnival.workers.implementation.FileUtils.FolderFilter;
import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.implementation.FileUtils.Evaluator.FileEvaluator;
import leo.carnival.workers.implementation.FileUtils.Evaluator.RegexEvaluator;
import leo.carnival.workers.implementation.FileUtils.FileFilter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TestStrReplacer implements Processor<String, String> {

    private RegexEvaluator fileNameEvaluator = RegexEvaluator.build();
    private AdvanceFileFilter advanceFileFilter = AdvanceFileFilter.build().setWorker(FileFilter.build(FileEvaluator.build(fileNameEvaluator)));
    private Pattern injectionPattern = Pattern.compile("(\\{[\\s]*\"Injection\":[\\s]*\"([\\w._]+)\"[\\s]*})");
    private File resourceFolder;

    @Override
    public String process(String sourceString) {
        Matcher injector = injectionPattern.matcher(sourceString);
        while (injector.find()) {
            try {
                String oldContent = injector.group(1);
                String dataFlowFileName = injector.group(2);

                fileNameEvaluator.setRegex(dataFlowFileName);
                List<File> extractionFile = advanceFileFilter.process(resourceFolder);

                if (extractionFile != null && !extractionFile.isEmpty()) {
                    String dataFlowContent = FileUtils.readFileToString(extractionFile.get(0)).trim();
                    Matcher matcher = Pattern.compile("\\[([\\s\\S]+)]").matcher(dataFlowContent);
                    String newContent = matcher.matches() ? matcher.group(1) : dataFlowContent;

                    //Search again for injection tag in extracted content
                    newContent = process(newContent);

                    sourceString = sourceString.replace(oldContent, newContent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sourceString;
    }

    @Override
    public String execute(String s) {
        return process(s);
    }


    public TestStrReplacer setExtractionDataFolder(FolderFilter folderFilter) {
        advanceFileFilter.setWorker(folderFilter);
        return this;
    }

    public TestStrReplacer setResourceFolder(File resourceFolder) {
        this.resourceFolder = resourceFolder;
        return this;
    }

    public static TestStrReplacer build() {
        return new TestStrReplacer();
    }

    public static TestStrReplacer build(FolderFilter folderFilter, File resourceFolder) {
        return new TestStrReplacer().setExtractionDataFolder(folderFilter).setResourceFolder(resourceFolder);
    }
}
