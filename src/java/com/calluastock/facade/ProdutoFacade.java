package com.calluastock.facade;

import com.calluastock.bean.Produto;
import com.calluastock.dao.ProdutoDao;
import java.util.List;


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

    public static Integer proximoCod() {
        return PDAO.proximoCod();
    }
}
