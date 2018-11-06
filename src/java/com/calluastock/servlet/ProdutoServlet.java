/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.servlet;

import com.calluastock.bean.Produto;
import com.calluastock.bean.Usuario;
import com.calluastock.util.Mensagem;
import com.calluastock.util.Validator;
import com.calluastock.facade.ProdutoFacade;
import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author renata
 */
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/Produto"})
@MultipartConfig(location = "/",fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)
public class ProdutoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String op = request.getParameter("op");
        HttpSession session = request.getSession(false);
        
        Usuario logado = null;
        if (session != null)
            logado = (Usuario)session.getAttribute("logado");
        
        if (logado != null) {
            switch(op) {
                case "listar":
                    listar(request, response);
                    break;
                case "carregarUmViaAjax":
                    carregarUmViaAjax(request, response);
                    break;
                case "removerUm":
                    removerUm(request, response);
                    break;
                case "adicionarUm":
                    adicionarUm(request, response);
                    break;
                case "editarUm":
                    editarUm(request, response);
                    break;
            }
        } else {
            Mensagem mensagem = new Mensagem("Acesso não autorizado !!!");
            mensagem.setTipo("error");
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login?op=dashboard");
            rd.forward(request, response);
        }
    }
    
    public void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Produto> produtos = ProdutoFacade.buscarTodos();
        Integer proximoCod = ProdutoFacade.proximoCod();

        request.setAttribute("produtos", produtos);
        request.setAttribute("proximoCod", proximoCod);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/tecnico/produtos.jsp");
        rd.forward(request, response);
    }
    
    public void carregarUmViaAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProduto = request.getParameter("idProduto");
        
        Produto produto = ProdutoFacade.buscarUm(Integer.parseInt(idProduto));

        // transforma o MAP em JSON
        String json = new Gson().toJson(produto);   

        // retorna o JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
    public void removerUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProduto = request.getParameter("idProduto");
        HttpSession session = request.getSession(false);
        
        ProdutoFacade.removerUm(Integer.parseInt(idProduto));

        Mensagem mensagem = new Mensagem("Produto removido com sucesso !!!");
        mensagem.setTipo("success");
        session = request.getSession();
        session.setAttribute("mensagem", mensagem);
        response.sendRedirect("Produto?op=listar");
    }

    public void adicionarUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Produto produto = carregarProduto(request);
        Mensagem mensagem = formValido(request, produto);
        HttpSession session = request.getSession(false);
        if (mensagem == null) {
            ProdutoFacade.adicionarUm(produto);
            mensagem = new Mensagem("Produto adicionado com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Produto?op=listar");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("produto", produto);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Produto?op=listar");
            rd.forward(request, response);
        }
    }

    public void editarUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Produto produto = carregarProduto(request);
        String idProduto = request.getParameter("idProduto");
        produto.setId(Integer.parseInt(idProduto));
        Mensagem mensagem = formValido(request, produto);
        HttpSession session = request.getSession(false);
        if (mensagem == null) {
            ProdutoFacade.editarUm(produto);
            mensagem = new Mensagem("Produto editado com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Produto?op=listar");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Produto?op=listar");
            rd.forward(request, response);
        }
    }
    
    public Produto carregarProduto(HttpServletRequest request) {
        Produto produto = new Produto();
        produto.setDescricao(request.getParameter("descricao"));
        if (request.getParameter("valor") != null && !"".equals(request.getParameter("valor")))
            produto.setValor(new BigDecimal(request.getParameter("valor").replaceAll("\\.", "").replaceAll(",", ".")));
           
        return produto;
    }
    
    public Mensagem formValido(HttpServletRequest request, Produto Produto) {
        Mensagem mensagem = Validator.validarDescricao(Produto.getDescricao());
        if (mensagem == null) {
            mensagem = Validator.validarValor(Produto.getValor());
        }
        
        return mensagem;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
