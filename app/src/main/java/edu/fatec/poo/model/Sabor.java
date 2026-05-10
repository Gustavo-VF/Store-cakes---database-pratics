package edu.fatec.poo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sabor extends Ingrediente {
    // Specialized Information
    private List<IngredienteSabor> IngredienteSabor = new ArrayList<>();

    public Sabor(long id, String nome, double preco, List<IngredienteSabor> IngredienteSabor) {
        super(id, nome, preco);
        this.IngredienteSabor = IngredienteSabor;
    }

    public Sabor() {
        super();
    }

    public List<IngredienteSabor> getIngredienteSabor() {
        return IngredienteSabor;
    }

    public void setIngredienteSabor(List<IngredienteSabor> ingredienteSabor) {
        this.IngredienteSabor = ingredienteSabor;
    }

    public double getPrecoTotal() {
        if (IngredienteSabor == null) return getPreco();

        return getPreco() + IngredienteSabor.stream()
                .mapToDouble(i -> i.getIngrediente().getPreco())
                .sum();
    }

    @Override
    public String toString() {
        return "Sabor{" +
                "ingredientes=" + IngredienteSabor +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sabor sabor = (Sabor) o;
        return Objects.equals(IngredienteSabor, sabor.IngredienteSabor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), IngredienteSabor);
    }
}
