package leo.webapplication.model;

import leo.engineData.testData.TestDataImpl;

/**
 * Created by leozhang on 8/31/16.
 * ...
 */
@SuppressWarnings("unused")
public class ApiTestData extends TestDataImpl<String, String> {
    private String name;
    private String[] ideas;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIdeas() {
        return ideas;
    }

    public void setIdeas(String[] ideas) {
        this.ideas = ideas;
    }

    @Override
    public String getTestingFlow() {
        return null;
    }

    @Override
    public String getSourceFileName() {
        return null;
    }

    @Override
    public void setSourceFileName(String name) {

    }

    @Override
    public String process(String s) {
        return String.format("i am %s, i have %d ideas", name, ideas == null ? 0 : ideas.length);
    }

    @Override
    public String execute(String s) {
        return process(s);
    }
}
