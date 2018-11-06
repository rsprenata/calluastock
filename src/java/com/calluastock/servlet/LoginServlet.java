/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.servlet;

import com.calluastock.bean.Chamado;
import com.calluastock.bean.StatusChamado;
import com.calluastock.facade.ChamadoFacade;
import com.calluastock.facade.LoginFacade;
import com.calluastock.util.Login;
import com.calluastock.util.Mensagem;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author renata
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

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
        String op = request.getParameter("op");
        
        HttpSession session = request.getSession(false);
        
        Usuario logado = null;
        if (session != null)
            logado = (Usuario)session.getAttribute("logado");
        
        //Em cima são os links que são publicos, sem validação de login
        if ("logar".equals(op) ||
            (session != null && logado != null)) {
            switch(op) {
                case "logar":
                    logar(request, response);
                    break;
                case "dashboard":
                    dashboard(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
            }
        } else {
            Mensagem mensagem = new Mensagem("Usuário deve se autenticar para acessar o sistema");
            mensagem.setTipo("error");
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/public/index.jsp");
            rd.forward(request, response);
        }
    }
    
    public void logar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Mensagem mensagem = formValido(request);
        HttpSession session = request.getSession(false);
        if (mensagem == null) {
            Usuario login = UsuarioFacade.loginValido(request.getParameter("cpf").replaceAll("\\W", "")
                                                    , request.getParameter("senha"));
            if (login != null) {
                session.setAttribute("logado", login);
                response.sendRedirect("Login?op=dashboard");
            } else {
                mensagem = new Mensagem("Usuário não encontrado.");
            }
        } 

        if (mensagem != null) {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/public/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }
    
    public void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario logado = (Usuario)session.getAttribute("logado");
        if (logado.isAdministrador()) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/dashboardadmin.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/tecnico/dashboardtecnico.jsp");
            rd.forward(request, response);
        }
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect("view/public/index.jsp");
    }
    
    public Mensagem formValido(HttpServletRequest request) {
        Mensagem mensagem = null;
        
        if (request.getParameter("cpf") == null || "".equals(request.getParameter("cpf"))) {
            mensagem = new Mensagem("Digite um CPF !!!");
        } else if (request.getParameter("senha") == null || "".equals(request.getParameter("senha"))) {
            mensagem = new Mensagem("Digite uma senha !!!");
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
