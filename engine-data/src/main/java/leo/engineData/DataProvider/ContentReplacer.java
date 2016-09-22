package leo.engineData.DataProvider;


import leo.carnival.workers.impl.Evaluators;
import leo.carnival.workers.impl.Evaluator.RegexEvaluator;
import leo.carnival.workers.impl.FileUtils.FileFilter;
import leo.carnival.workers.impl.Processors;
import leo.carnival.workers.prototype.Processor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ContentReplacer implements Processor<String, String> {

    private Pattern injectionPattern = Pattern.compile("(\\{[\\s]*\"Injection\":[\\s]*\"([\\w._]+)\"[\\s]*})");
    private File dataSourceFolder;
    private RegexEvaluator regexEvaluator = Evaluators.RegexEvaluator(null);
    private FileFilter filter = Processors.FileFilter(Evaluators.FileEvaluator(regexEvaluator));

    @Override
    public String process(String sourceString) {
        Matcher injector = injectionPattern.matcher(sourceString);
        while (injector.find()) {
            try {
                String oldContent = injector.group(1);
                String dataFlowFileName = injector.group(2);

                regexEvaluator.setRegex(dataFlowFileName);
                List<File> extractionFile = filter.process(dataSourceFolder);

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

    public void process(Map<File, String> contents){
        for(File key : contents.keySet())
            contents.put(key, process(contents.get(key)));
    }

    @Override
    public String execute(String s) {
        return process(s);
    }


    public ContentReplacer setExtractionDataFolder(File dataSourceFolder) {
        this.dataSourceFolder = dataSourceFolder;
        return this;
    }

    public static ContentReplacer build() {
        return new ContentReplacer();
    }

    public static ContentReplacer build(File dataSourceFolder) {
        return new ContentReplacer().setExtractionDataFolder(dataSourceFolder);
    }
}
