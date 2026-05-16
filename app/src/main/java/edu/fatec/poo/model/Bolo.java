package edu.fatec.poo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bolo {
    // Base Information
    private long id = 0;
    private String nome = "";

    // Specialized information
    private int pesoGrama = 0;
    private Sabor saborRecheio = new Sabor();
    private Sabor saborMassa = new Sabor();

    // Product Information
    private double preco = 0.0;
    private boolean encomendavel = false;
    private boolean prontaEntrega = false;


    public Bolo(long id, String nome, int pesoGrama, Sabor saborRecheio, Sabor saborMassa, double preco, boolean encomendavel, boolean prontaEntrega) {
        this.id = id;
        this.nome = nome;
        this.pesoGrama = pesoGrama;
        this.saborRecheio = saborRecheio;
        this.saborMassa = saborMassa;
        this.preco = preco;
        this.encomendavel = encomendavel;
        this.prontaEntrega = prontaEntrega;
    }

    public Bolo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPesoGrama() {
        return pesoGrama;
    }

    public void setPesoGrama(int pesoGrama) {
        this.pesoGrama = pesoGrama;
    }

    public Sabor getSaborRecheio() {
        return saborRecheio;
    }

    public void setSaborRecheio(Sabor saborRecheio) {
        this.saborRecheio = saborRecheio;
    }

    public Sabor getSaborMassa() {
        return saborMassa;
    }

    public void setSaborMassa(Sabor saborMassa) {
        this.saborMassa = saborMassa;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isEncomendavel() {
        return encomendavel;
    }

    public void setEncomendavel(boolean encomendavel) {
        this.encomendavel = encomendavel;
    }

    public boolean isProntaEntrega() {
        return prontaEntrega;
    }

    public void setProntaEntrega(boolean prontaEntrega) {
        this.prontaEntrega = prontaEntrega;
    }


    @Override
    public String toString() {
        return "Bolo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pesoGrama=" + pesoGrama +
                ", saborRecheio=" + saborRecheio +
                ", saborMassa=" + saborMassa +
                ", preco=" + preco +
                ", encomendavel=" + encomendavel +
                ", prontaEntrega=" + prontaEntrega +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bolo bolo = (Bolo) o;
        return id == bolo.id && pesoGrama == bolo.pesoGrama && Double.compare(preco, bolo.preco) == 0 && encomendavel == bolo.encomendavel && prontaEntrega == bolo.prontaEntrega && Objects.equals(nome, bolo.nome) && Objects.equals(saborRecheio, bolo.saborRecheio) && Objects.equals(saborMassa, bolo.saborMassa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, pesoGrama, saborRecheio, saborMassa, preco, encomendavel, prontaEntrega);
    }
}
