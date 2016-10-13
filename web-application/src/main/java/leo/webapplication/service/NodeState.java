package leo.webapplication.service;

import java.io.Serializable;

/**
 * Created by leo_zlzhang on 10/13/2016.
 * State of node
 */
public class NodeState implements Serializable{
    private boolean disabled;
    private boolean opened;
    private boolean selected;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
