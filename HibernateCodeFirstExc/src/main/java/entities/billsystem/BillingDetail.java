package entities.billsystem;


import entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@DiscriminatorColumn(name = "type")
public class BillingDetail extends BaseEntity {

    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User user;

    public BillingDetail() {
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
