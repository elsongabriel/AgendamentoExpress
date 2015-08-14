package br.com.elsonsofts.studiodassobrancelhas.basicas;

import java.io.Serializable;

/**
 * Created by Elson on 06/08/2015.
 */
public class Usuario implements Serializable {
    public Integer id;
    public String login;
    public String senha;
    public String nome;
    public Integer permissao;
    public String email;
    public String telefone;
    public String dataCadastro;
    public boolean ativo;

    public Usuario(Integer id, String login, String senha, String nome, Integer permissao, String email, String telefone, String dataCadastro, boolean ativo) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.permissao = permissao;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPermissao() {
        return permissao;
    }

    public void setPermissao(Integer permissao) {
        this.permissao = permissao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario() {
    }
}
