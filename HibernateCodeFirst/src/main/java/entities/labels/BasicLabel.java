package entities.labels;

import entities.shampoos.BasicShampoo;

import javax.persistence.*;

@Entity
@Table(name = "labels")
public class BasicLabel implements Label {
    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String subtitle;

    @OneToOne(targetEntity = BasicShampoo.class,mappedBy = "label",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BasicShampoo shampoo;

    protected BasicLabel(){

    }

    public BasicLabel(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubtitle() {
        return this.subtitle;
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
