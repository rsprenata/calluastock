/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.util;

/**
 *
 * @author renata
 * Classe auxiliar para as mensagens mostradas no sistema através do sweet alert.
 */
public class Mensagem {
    private String texto;
    private String tipo;

    public Mensagem() {}

    public Mensagem(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
