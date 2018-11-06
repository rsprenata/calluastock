/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.util;

import com.calluastock.bean.Usuario;
import com.calluastock.bean.Endereco;
import com.calluastock.facade.ClienteFacade;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author renata
 * Classe auxiliar para fazer as validações back-end do sistema
 */
public class Validator {
    public static Mensagem validarSenha(String senha, String confirmacaoSenha) {
        Mensagem mensagem = null;
        
        if (senha== null || "".equals(senha)) {
            mensagem = new Mensagem("Senha é obrigatória !!!");
        } else if (senha.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na senha !!!");
        } else if (confirmacaoSenha == null || "".equals(confirmacaoSenha)) {
            mensagem = new Mensagem("Confirmação de senha é obrigatória !!!");
        } else if (confirmacaoSenha.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na confirmação da senha !!!");
        } else if (!senha.equals(confirmacaoSenha)) {
            mensagem = new Mensagem("Senha e confirmação diferentes !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarSenhaAtual(Usuario usuario, String senhaAtual) {
        Mensagem mensagem = null;
        
        if (senhaAtual.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na senha atual !!!");
        } else if (!UsuarioFacade.senhaAtualValida(usuario, senhaAtual)) {
            mensagem = new Mensagem("Senha atual inválida !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarCpf(String cpf) {
        Mensagem mensagem = null;
        
        if (cpf == null || "".equals(cpf)) {
            mensagem = new Mensagem("CPF é obrigatório !!!");
        } else if (!cpfValido(cpf)) {
            mensagem = new Mensagem("CPF inválido !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarNome(String nome) {
        Mensagem mensagem = null;
        
        if (nome == null || "".equals(nome)) {
            mensagem = new Mensagem("Nome é obrigatório !!!");
        } else if (nome.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no nome !!!");
        }
        
        return mensagem;
    }
    
    public static boolean cpfValido(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {              
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0         
        // (48 eh a posicao de '0' na tabela ASCII)         
            num = (int)(CPF.charAt(i) - 48); 
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
