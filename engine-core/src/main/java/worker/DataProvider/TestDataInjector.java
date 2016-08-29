package worker.DataProvider;


import leo.carnival.workers.prototype.Processor;
import leo.carnival.workers.implementation.CollectionUtils.FirstElementPicker;
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
public class TestDataInjector implements Processor<String, String> {

    private File extractFileFolder;
    private Pattern injectionPattern = Pattern.compile("(\\{[\\s]*\"Injection\":[\\s]*\"([\\w._]+)\"[\\s]*})");

    @Override
    public String process(String sourceString) {
        Matcher injector = injectionPattern.matcher(sourceString);
        while (injector.find()) {
            try {
                String oldContent = injector.group(1);
                String dataFlowFileName = injector.group(2);


                List<File> files = FileFilter.build(FileEvaluator.build(RegexEvaluator.build(dataFlowFileName))).process(extractFileFolder);
                File file = new FirstElementPicker<File>().process(files);
                String dataFlowContent = FileUtils.readFileToString(file).trim();
                Matcher matcher = Pattern.compile("\\[([\\s\\S]+)]").matcher(dataFlowContent);
                String newContent = matcher.matches() ? matcher.group(1) : dataFlowContent;

                //Search again for injection tag in extracted content
                newContent = process(newContent);

                sourceString = sourceString.replace(oldContent, newContent);
            } catch (IOException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sourceString;
    }

    @Override
    public String execute(String s) {
        return process(s);
    }


    public TestDataInjector setExtractionDataFolder(File folder){
        if(folder == null || !folder.exists())
            return this;

        this.extractFileFolder = folder;
        return this;
    }

    public static TestDataInjector build() {
        return new TestDataInjector();
    }

    public static TestDataInjector build(File extractFileFolder) {
        return new TestDataInjector().setExtractionDataFolder(extractFileFolder);
    }
}
