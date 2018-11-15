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

    public static void adicionarUm(Usuario usuario, String url) {
        UDAO.adicionarUm(usuario, url);
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

    public static Boolean alterarSenhaTokenValido(String token, Integer idUsuario) {
        return UDAO.alterarSenhaTokenValido(token, idUsuario);
    }

    public static void alterarSenha(Usuario usuario) {
        UDAO.alterarSenha(usuario);
    }
}
