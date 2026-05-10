package edu.fatec.poo.model;

import java.util.Objects;

public class IngredienteSabor {
    private Sabor sabor;
    private Ingrediente ingrediente;
    private double quantidade;

    public IngredienteSabor(Sabor sabor, Ingrediente ingrediente, double quantidade) {
        this.sabor = sabor;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
