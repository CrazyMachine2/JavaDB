package mostwanted.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "districts")
public class District  extends  BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    public District() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
