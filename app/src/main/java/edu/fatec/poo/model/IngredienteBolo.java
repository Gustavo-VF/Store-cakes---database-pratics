package edu.fatec.poo.model;

import java.util.Objects;

public class IngredienteBolo {
    private Bolo bolo;
    private Ingrediente ingrediente;
    private double quantidade;

    public IngredienteBolo() {
    }


    public IngredienteBolo(Bolo bolo, Ingrediente ingrediente, double quantidade) {
        this.bolo = bolo;
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public Bolo getBolo() {
        return bolo;
    }

    public void setBolo(Bolo bolo) {
        this.bolo = bolo;
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
