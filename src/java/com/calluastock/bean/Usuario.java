/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.bean;

/**
 *
 * @author renata
 */
public class Usuario implements java.io.Serializable {
	private Integer id;
	private String nome;
	private String cpf;
        private String email;
	private String senha;
    private boolean administrador;
    
    public Usuario() {}

    public Integer getId() {
    	return this.id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public String getNome() {
    	return this.nome;
    }

    public void setNome(String nome) {
    	this.nome = nome;
    }

    public String getCpf() {
    	return this.cpf;
    }

    public void setCpf(String cpf) {
    	this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
    	return this.senha;
    }

    public void setSenha(String senha) {
    	this.senha = senha;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}
