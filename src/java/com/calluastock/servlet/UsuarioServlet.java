/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.servlet;

import com.calluastock.bean.Usuario;
import com.calluastock.bean.Usuario;
import com.calluastock.util.Mensagem;
import com.calluastock.util.Validator;
import com.calluastock.facade.UsuarioFacade;
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
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/Usuario"})
@MultipartConfig(location = "/",fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)
public class UsuarioServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        Usuario logado = (Usuario)session.getAttribute("logado");
        
        List<Usuario> usuarios = UsuarioFacade.buscarTodosMenosUm(logado);

        request.setAttribute("usuarios", usuarios);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/usuarios.jsp");
        rd.forward(request, response);
    }
    
    public void carregarUmViaAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUsuario = request.getParameter("idUsuario");
        
        Usuario usuario = UsuarioFacade.carregarUm(Integer.parseInt(idUsuario));

        // transforma o MAP em JSON
        String json = new Gson().toJson(usuario);   

        // retorna o JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
    public void removerUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUsuario = request.getParameter("idUsuario");
        HttpSession session = request.getSession(false);
        
        UsuarioFacade.removerUm(Integer.parseInt(idUsuario));

        Mensagem mensagem = new Mensagem("Usuario removido com sucesso !!!");
        mensagem.setTipo("success");
        session = request.getSession();
        session.setAttribute("mensagem", mensagem);
        response.sendRedirect("Usuario?op=listar");
    }

    public void adicionarUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = carregarUsuario(request);
        Mensagem mensagem = formValido(request, usuario);
        HttpSession session = request.getSession(false);
        if (mensagem == null) {
            UsuarioFacade.adicionarUm(usuario);
            mensagem = new Mensagem("Usuario adicionado com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Usuario?op=listar");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("usuario", usuario);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Usuario?op=listar");
            rd.forward(request, response);
        }
    }

    public void editarUm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = carregarUsuario(request);
        String idUsuario = request.getParameter("idUsuario");
        usuario.setId(Integer.parseInt(idUsuario));
        Mensagem mensagem = formValido(request, usuario);
        HttpSession session = request.getSession(false);
        if (mensagem == null) {
            UsuarioFacade.editarUm(usuario);
            mensagem = new Mensagem("Usuario editado com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Usuario?op=listar");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Usuario?op=listar");
            rd.forward(request, response);
        }
    }
    
    public Usuario carregarUsuario(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        
        usuario.setNome(request.getParameter("nome"));
        usuario.setCpf(request.getParameter("cpf"));
        if (usuario.getCpf() != null) usuario.setCpf(usuario.getCpf().replaceAll("\\W", ""));
        if(request.getParameter("administrador") == null || "".equals(request.getParameter("administrador")) || "false".equals(request.getParameter("administrador"))) {
            usuario.setAdministrador(false);
        } else {
            usuario.setAdministrador(true);
        }
           
        return usuario;
    }
    
    public Mensagem formValido(HttpServletRequest request, Usuario usuario) {
        Mensagem mensagem = Validator.validarNome(usuario.getNome());
        if (mensagem == null) {
            mensagem = Validator.validarCpf(usuario.getCpf());
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
