package leo.offlineExectionEngine.TestCaseFactory.DataProvider;

import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FileFilter;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Filter.FileSearchUtil;
import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Processor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringInjector implements Processor<String, String>{

    private File extractFileFolder;
    private Pattern injectionPattern = Pattern.compile("(\\{[\\s]*\"Injection\":[\\s]*\"([\\w._]+)\"[\\s]*})");

    public StringInjector(File extractionFileFolder) {
        this.extractFileFolder = extractionFileFolder;
    }

    @Override
    public String process(String sourceString) {
        Matcher injector = injectionPattern.matcher(sourceString);
        while (injector.find()) {
            try {
                String oldContent = injector.group(1);
                String newContent = extractContentFromFile(injector.group(2), extractFileFolder);

                //Search again for injection tag in extracted content
                newContent = process(newContent);

                sourceString = sourceString.replace(oldContent, newContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sourceString;
    }

    private String extractContentFromFile(String extractionFileName, File extractionFileFolder) throws IOException {
        File extractionFile = FileSearchUtil.findFirstFile(extractionFileFolder, new FileFilter(extractionFileName));
        String extractedContent = FileUtils.readFileToString(extractionFile).trim();
        Matcher matcher = Pattern.compile("\\[([\\s\\S]+)]").matcher(extractedContent);
        return matcher.matches() ? matcher.group(1) : extractedContent;
    }
}
