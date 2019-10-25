package entities.ingredients;

import java.math.BigDecimal;

public abstract class BasicChemicalIngredient extends BasicIngredient
        implements ChemicalIngredient{

    private String chemicalFormula;

    public BasicChemicalIngredient(String name, BigDecimal priceString,String chemicalFormula) {
        super(name,priceString);
        this.chemicalFormula = chemicalFormula;
    }

    @Override
    public String getChemicalFormula() {
        return this.chemicalFormula;
    }

    @Override
    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }
}





















