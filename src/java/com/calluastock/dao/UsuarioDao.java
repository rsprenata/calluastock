package com.calluastock.dao;

import com.calluastock.bean.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class UsuarioDao {
    public List<Usuario> buscarTodosMenosUm(Usuario um) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id != ?");
            stmt.setInt(1, um.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getInt("id"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
                
                usuarios.add(usuario);
            }
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
        
        return usuarios;
    }
    
    public Usuario loginValido(String cpf, String senha) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes(), 0, senha.length());
            senha = new BigInteger(1, md.digest()).toString(16);

            stmt = connection.prepareStatement("SELECT * FROM usuario WHERE cpf = ? AND senha = ?");
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
                
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
            }
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
        
        return usuario;
    }

    public Usuario carregarUm(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
                
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
        
        return usuario;
    }
    
    public void adicionarUm(Usuario usuario, String url) {
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            connection.setAutoCommit(false);
            
            stmt = connection.prepareStatement("INSERT INTO Usuario (nome, cpf, administrador) VALUES "
                + "(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setBoolean(3, usuario.isAdministrador());
            
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha no cadastro do usuário!");
                }
            }
            
            //COLOCAR O TOKEN PARA CRIAÇÃO DE SENHA - SEGURANÇA
            stmt = connection.prepareStatement("INSERT INTO AlterarSenhaToken (idUsuario, token) "
                                                + "VALUES (?, ?)");
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, token);
            stmt.executeUpdate();
            
            //ENVIAR O EMAIL PARA O TÉCNICO
            Email e = new SimpleEmail();
            e.setDebug(true);
            e.setHostName("smtp.googlemail.com");
            e.setSmtpPort(465);
            e.setAuthenticator(new DefaultAuthenticator("calluasystems@gmail.com", "callua@)!*"));
            e.setSSLOnConnect(true);
            e.setFrom("calluasystems@gmail.com");
            e.setSubject("Callua Stock - Cadastro de usuário - Criação de senha");
            e.setMsg(url + "/calluastock/Usuario?op=criarSenhaForm&token=" + token + "&idUsuario=" + usuario.getId());
            e.addTo(usuario.getEmail());
            e.send();
            
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }

    public boolean senhaAtualValida(Usuario usuario, String senhaAtual) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senhaAtual.getBytes(), 0, senhaAtual.length());
            senhaAtual= new BigInteger(1, md.digest()).toString(16);
            
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ? AND senha = ?");
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, senhaAtual);
            rs = stmt.executeQuery();
                
            return rs.next();
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
    
    public void removerUm(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("DELETE FROM Usuario WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }

    public void editarUm(Usuario usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Usuario SET cpf = ?, nome = ?, administrador = ? WHERE id = ?");
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getNome());
            stmt.setBoolean(3, usuario.isAdministrador());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
    
    public Boolean alterarSenhaTokenValido(String token, Integer idUsuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM AlterarSenhaToken WHERE token = ? AND idUsuario = ?");
            stmt.setString(1, token);
            stmt.setInt(2, idUsuario);
            rs = stmt.executeQuery();
                
            return rs.next();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
    
    public void alterarSenha(Usuario usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            connection.setAutoCommit(false);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(usuario.getSenha().getBytes(), 0, usuario.getSenha().length());
            usuario.setSenha(new BigInteger(1, md.digest()).toString(16));
            
            stmt = connection.prepareStatement("UPDATE Usuario SET senha = ? WHERE id = ?");
            stmt.setString(1, usuario.getSenha());
            stmt.setInt(2, usuario.getId());
            
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement("DELETE FROM AlterarSenhaToken WHERE idUsuario = ?");
            stmt.setInt(1, usuario.getId());
            
            stmt.executeUpdate();
            
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
}
