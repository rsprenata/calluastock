package com.calluastock.facade;

import com.calluastock.bean.Usuario;
import com.calluastock.dao.UsuarioDao;
import java.util.List;


public class UsuarioFacade {
    private static final UsuarioDao UDAO = new UsuarioDao();
    
    public static Usuario loginValido(String cpf, String senha) {
        return UDAO.loginValido(cpf, senha);
    }

    public static Usuario carregarUm(Integer id) {
        return UDAO.carregarUm(id);
    }

    public static void adicionarUm(Usuario usuario) {
        UDAO.adicionarUm(usuario);
    }

    public static boolean senhaAtualValida(Usuario usuario, String senhaAtual) {
        return UDAO.senhaAtualValida(usuario, senhaAtual);
    }

    public static List<Usuario> buscarTodosMenosUm(Usuario um) {
        return UDAO.buscarTodosMenosUm(um);
    }

    public static void removerUm(Integer id) {
        UDAO.removerUm(id);
    }

    public static void editarUm(Usuario produto) {
        UDAO.editarUm(produto);
    }
}
