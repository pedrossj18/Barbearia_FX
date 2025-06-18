package project_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import project_model.Usuario;

public class UsuarioDAO {
	public Usuario autenticar(String login, String senha) {
	    String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

	    try (Connection conn = Conexao.getConexao();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, login);
	        stmt.setString(2, senha);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Usuario usuario = new Usuario();
	            usuario.setId(rs.getInt("id"));
	            usuario.setLogin(rs.getString("login"));
	            usuario.setSenha(rs.getString("senha"));
	            usuario.setNomeBarbearia(rs.getString("nome_barbearia"));
	            return usuario;
	        }

	    } catch (SQLException e) {
	        System.err.println("Erro ao autenticar usuário: " + e.getMessage());
	    }

	    return null; // Login inválido
	}
    
//Retorna o objeto Usuario completo após login bem-sucedido
public Usuario buscarUsuarioPorLogin(String login) {
    String sql = "SELECT * FROM usuarios WHERE login = ?";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setNomeBarbearia(rs.getString("nome_barbearia"));
            return usuario;
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar usuário: " + e.getMessage());
    }

    return null;
	}
}