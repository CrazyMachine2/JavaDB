package entities.labels;

import java.io.Serializable;

public interface Label extends Serializable {
    String getTitle();
    void setTitle(String title);

    int getId();
    void setId(int id);

    String getSubtitle();
    void setSubtitle(String subtitle);

}
