package edu.fatec.poo.model;

import java.util.UUID;

public class Cliente {
    private UUID Id;
    private String nome;
    private String email;
    private String senha;
    private String enderecoLogradouro;
    private String enderecoCep;
    private Integer enderecoNum;
    private String enderecoComplemento;
    private Role role;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEnderecoLogradouro() {
        return enderecoLogradouro;
    }

    public void setEnderecoLogradouro(String enderecoLogradouro) {
        this.enderecoLogradouro = enderecoLogradouro;
    }

    public String getEnderecoCep() {
        return enderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        this.enderecoCep = enderecoCep;
    }

    public Integer getEnderecoNum() {
        return enderecoNum;
    }

    public void setEnderecoNum(Integer enderecoNum) {
        this.enderecoNum = enderecoNum;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", enderecoLogradouro='" + enderecoLogradouro + '\'' +
                ", enderecoCep='" + enderecoCep + '\'' +
                ", enderecoNum=" + enderecoNum +
                ", enderecoComplemento='" + enderecoComplemento + '\'' +
                ", role=" + role +
                '}';
    }
}
