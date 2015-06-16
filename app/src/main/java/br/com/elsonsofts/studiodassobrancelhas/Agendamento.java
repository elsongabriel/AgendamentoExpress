package br.com.elsonsofts.studiodassobrancelhas;

import java.io.Serializable;

public class Agendamento implements Serializable {
    public Integer id;
    public String nome;
    public String telefone1;
    public String telefone2;
    public String endereco;
    public String pontoReferencia;
    public String email;
    public String dataCadastro;
    public String dataAgendamento;
    public boolean confirmado;
    public boolean ativo;

    public Agendamento(Integer id, String nome, String telefone1, String telefone2,
                       String endereco, String pontoReferencia, String email, String dataCadastro,
                       String dataAgendamento, boolean confirmado, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.endereco = endereco;
        this.pontoReferencia = pontoReferencia;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.dataAgendamento = dataAgendamento;
        this.confirmado = confirmado;
        this.ativo = ativo;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Agendamento(){}
}

