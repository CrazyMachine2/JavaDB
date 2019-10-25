package entities.billsystem;

import entities.BaseEntity;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "bank_account")
public class BankAccount extends BillingDetail {
    @Column(name = "name")
    private String name;

    @Column(name = "swift_code")
    private String swiftCode;

    public BankAccount() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwiftCode() {
        return this.swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
