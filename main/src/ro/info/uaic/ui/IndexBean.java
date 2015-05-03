package ro.info.uaic.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by lotus on 03.05.2015.
 */
@Component
@Scope("view")
public class IndexBean implements Serializable
{
    private boolean selected = true;
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getBoxValueDescription() {
        if(selected)
            return "selected";
        else
            return "unselected";
    }
}
