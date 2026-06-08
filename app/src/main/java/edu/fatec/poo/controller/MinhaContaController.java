package edu.fatec.poo.controller;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;

public class MinhaContaController {

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty cep = new SimpleStringProperty("");
    private StringProperty numero = new SimpleStringProperty("");
    private StringProperty complemento = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    private Cliente clienteLogado;

    public MinhaContaController() {
        carregarDados();
        nome.set(clienteLogado.getNome());
        email.set(clienteLogado.getEmail());
        endereco.set(clienteLogado.getEnderecoLogradouro());
        cep.set(clienteLogado.getEnderecoCep());
        numero.set(clienteLogado.getEnderecoNum().toString());
        complemento.set(clienteLogado.getEnderecoComplemento());
    }

    public void carregarDados() {
        this.clienteLogado = Contexto.getClienteLogado();
    }

    public void Editar() throws SQLException, ClassNotFoundException, IllegalArgumentException {
        SqlDaoFactory.getClienteDao().update(toEntity());
        this.clienteLogado = toEntity();
        Contexto.setClienteLogado(this.clienteLogado);
    }

    public void Excluir() throws SQLException, ClassNotFoundException, IllegalArgumentException {
        System.out.println(clienteLogado);
        if (SqlDaoFactory.getClienteDao().delete(clienteLogado).isPresent()) {
            System.out.println(clienteLogado);
            Contexto.sair();
        } else {
            System.out.println(clienteLogado);
            throw new IllegalArgumentException("O Aluno não pode ser deletado.");
        }
    }


    public Cliente toEntity() {
        Cliente clienteAtualizado = new Cliente();

        clienteAtualizado.setId(clienteLogado.getId());
        clienteAtualizado.setSenha(clienteLogado.getSenha());
        clienteAtualizado.setNome(nome.get());
        clienteAtualizado.setEmail(email.get());
        clienteAtualizado.setEnderecoLogradouro(endereco.get());
        clienteAtualizado.setEnderecoCep(cep.get());
        clienteAtualizado.setEnderecoNum(Integer.parseInt(numero.get()));
        clienteAtualizado.setEnderecoComplemento(complemento.get());
        clienteAtualizado.setRole(clienteLogado.getRole());
        return clienteAtualizado;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public StringProperty cepProperty() {
        return cep;
    }

    public StringProperty numeroProperty() {
        return numero;
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public String getCep() {
        return cep.get();
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public String getNumero() {
        return numero.get();
    }

    public void setNumero(String numero) {
        this.numero.set(numero);
    }

    public String getComplemento() {
        return complemento.get();
    }

    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

    public String getMensagem() {
        return mensagem.get();
    }

    public void setMensagem(String mensagem) {
        this.mensagem.set(mensagem);
    }

    public Cliente getClienteLogado() {
        return clienteLogado;
    }

    public void setClienteLogado(Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
    }
}