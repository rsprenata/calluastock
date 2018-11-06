/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.bean;

import java.math.BigDecimal;

/**
 *
 * @author renata
 */
public class Produto implements java.io.Serializable {
	private Integer id;
	private String descricao;
	private BigDecimal valor;
    
    public Produto() {}

    public Integer getId() {
    	return this.id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public String getDescricao() {
    	return this.descricao;
    }

    public void setDescricao(String descricao) {
    	this.descricao = descricao;
    }

    public BigDecimal getValor() {
    	return this.valor;
    }

    public void setValor(BigDecimal valor) {
    	this.valor = valor;
    }
}
