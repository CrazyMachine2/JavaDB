package entities.shampoos;

import entities.labels.BasicLabel;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class PinkPanther extends BasicShampoo {

    public PinkPanther() {
        super(new BigDecimal(8.50),
                "Pink Panther",
                Size.MEDIUM,
                new BasicLabel
                        ("Pink Panther","It's made of Lavender and Nettle") {
                });
    }
}
