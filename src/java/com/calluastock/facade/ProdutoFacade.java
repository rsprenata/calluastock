package com.calluastockstock.facade;

import com.calluastock.bean.Produto;
import com.calluastock.dao.ProdutoDao;


public class ProdutoFacade {
    private static final ProdutoDao PDAO = new ProdutoDao();
    
    public static void adicionarUm(Produto produto) {
        PDAO.adicionarUm(produto);
    }

    public static List<Produto> buscarTodos() {
        return PDAO.buscarTodos();
    }

    public static Produto buscarUm(Integer id) {
        return PDAO.buscarUm(id);
    }

    public static void editarUm(Produto produto) {
        PDAO.editarUm(produto);
    }

    public static void removerUm(Integer id) {
        PDAO.removerUm(id);
    }
}
