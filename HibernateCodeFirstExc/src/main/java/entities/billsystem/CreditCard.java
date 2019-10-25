package entities.billsystem;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DiscriminatorValue(value = "credit_card")
public class CreditCard extends BillingDetail{
    @Column(name = "card_type")
    private String cardType;

    @Column(name = "expiration_month",length = 12)
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

    public CreditCard() {
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return this.expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
}
