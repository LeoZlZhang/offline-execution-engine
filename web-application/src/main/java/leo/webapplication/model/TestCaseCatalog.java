package leo.webapplication.model;

import leo.webapplication.service.NodeState;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by leo_zlzhang on 10/12/2016.
 * Catalog of test case
 */
@SuppressWarnings("unused")
public class TestCaseCatalog implements Serializable {
    @Id
    private String id;
    private String text;
    /**
     * "jstree-file" as file
     * null as folder
     */
    private Object icon;
    private TestCaseCatalog[] children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public TestCaseCatalog[] getChildren() {
        return children;
    }

    public void setChildren(TestCaseCatalog[] children) {
        this.children = children;
    }
}
