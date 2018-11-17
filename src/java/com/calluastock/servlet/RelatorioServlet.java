package com.calluastock.servlet;

import com.calluastock.bean.Usuario;
import com.calluastock.dao.ConnectionFactory;
import com.calluastock.util.Mensagem;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author renata.pereira
 */
@WebServlet(name = "RelatorioServlet", urlPatterns = {"/Relatorio"})
public class RelatorioServlet extends HttpServlet {

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
        
        if (logado != null && logado.isAdministrador()) {
            switch(op) {
                case "listar":
                        listar(request, response);
                    break;
                case "gerar":
                    gerar(request, response);
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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/relatoriosListar.jsp");
        rd.forward(request, response);
    }
    
    public void gerar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relatorio = request.getParameter("relatorio");
        HashMap params = new HashMap();
        
        //AQUI É CONFIGURA OS PARAMETROS DOS RELATORIOS QUE NECESSITAM
        if ("chamadosPorData".equals(relatorio)) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                params.put("dataInicio", new java.sql.Date(formato.parse(request.getParameter("dataInicio")).getTime()));
                params.put("dataFim", new java.sql.Date(formato.parse(request.getParameter("dataFim")).getTime()));
            } catch (ParseException ex) {
                Logger.getLogger(RelatorioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            gerarRelatorio(relatorio, params, request, response);
        } catch (Exception ex) {
            Mensagem mensagem = new Mensagem("Erro ao gerar relatório");
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/relatoriosListar.jsp");
            rd.forward(request, response);
        }
    }
    
    public void gerarRelatorio(String relatorio, HashMap params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Connection con = null;
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            con = connectionFactory.getConnection();
            
            String jasper = request.getContextPath() + "/relatorios/"+ relatorio +".jasper";
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            
            URL jasperURL = new URL(host + jasper);
            
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, con);
            
            if (bytes != null) {
                response.setContentType("application/pdf");
                
                OutputStream ops = response.getOutputStream();
                
                ops.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
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