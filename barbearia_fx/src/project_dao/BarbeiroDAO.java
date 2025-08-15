package project_dao;

import project_model.Barbeiro;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarbeiroDAO {

    // Inserir barbeiro
	public boolean inserir(Barbeiro barbeiro) {
	    String sql = "INSERT INTO barbeiros (nome, especialidade, telefone) VALUES (?, ?, ?)";

	    try (Connection conn = Conexao.getConexao();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, barbeiro.getNome());
	        stmt.setString(2, barbeiro.getEspecialidade());
	        stmt.setString(3, barbeiro.getTelefone());

	        int linhasAfetadas = stmt.executeUpdate();
	        return linhasAfetadas > 0;

	    } catch (SQLException e) {
	        System.err.println("Erro ao inserir barbeiro: " + e.getMessage());
	        return false;
	    }
	}

    // Listar barbeiros
    public List<Barbeiro> listar() {
        List<Barbeiro> barbeiros = new ArrayList<>();
        String sql = "SELECT * FROM barbeiros";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro();
                barbeiro.setId(rs.getInt("id"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setEspecialidade(rs.getString("especialidade"));
                barbeiro.setTelefone(rs.getString("telefone"));

                barbeiros.add(barbeiro);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar barbeiros: " + e.getMessage());
        }

        return barbeiros;
    }

    // Atualizar barbeiro
    public void atualizar(Barbeiro barbeiro) {
        String sql = "UPDATE barbeiros SET nome = ?, especialidade = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getEspecialidade());
            stmt.setString(3, barbeiro.getTelefone());
            stmt.setInt(4, barbeiro.getId());

            stmt.executeUpdate();
            System.out.println("Barbeiro atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar barbeiro: " + e.getMessage());
        }
    }
    
    // Verifica se o barbeiro está vinculado a algum agendamento
    public boolean barbeiroEstaAgendado(int idBarbeiro) {
        String sql = "SELECT COUNT(*) FROM agendamentos WHERE barbeiro_id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBarbeiro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Está agendado
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar agendamento do barbeiro: " + e.getMessage());
        }

        return false; // Não está agendado
    }

    // Excluir barbeiro
    public boolean excluir(int idBarbeiro) {
        if (!existeBarbeiroPorId(idBarbeiro)) {
            System.out.println("ID " + idBarbeiro + " não encontrado. Nenhum barbeiro foi excluído.");
            return false;
        }

        if (barbeiroEstaAgendado(idBarbeiro)) {
            System.out.println("O barbeiro com ID " + idBarbeiro + " está agendado e não pode ser excluído.");
            return false;
        }

        String sql = "DELETE FROM barbeiros WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBarbeiro);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Barbeiro excluído com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum barbeiro foi excluído.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir barbeiro: " + e.getMessage());
            return false;
        }
    }
    
    // Esse método faz um SELECT COUNT(*) para verificar se o ID existe.
    public boolean existeBarbeiroPorId(int idBarbeiro) {
        String sql = "SELECT COUNT(*) FROM barbeiros WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBarbeiro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar barbeiro: " + e.getMessage());
        }

        return false;
    }
    
    //Este metodo faz a busca no banco de dados para exclusão por ID
    public Barbeiro buscarPorId(int id) {
        String sql = "SELECT * FROM barbeiros WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Barbeiro barbeiro = new Barbeiro();
                barbeiro.setId(rs.getInt("id"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setEspecialidade(rs.getString("especialidade"));
                barbeiro.setTelefone(rs.getString("telefone"));
                return barbeiro;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar barbeiro por ID: " + e.getMessage());
        }

        return null;
    }
    
    //Este metodo faz a busca no banco de dados para exclusão por nome
    public List<Barbeiro> buscarPorNome(String nome) {
        List<Barbeiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM barbeiros WHERE nome LIKE ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro();
                barbeiro.setId(rs.getInt("id"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setEspecialidade(rs.getString("especialidade"));
                barbeiro.setTelefone(rs.getString("telefone"));
                lista.add(barbeiro);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar barbeiro por nome: " + e.getMessage());
        }

        return lista;
    }
    
    //Este metodo faz a busca no banco de dados para exclusão por Telefone
    public List<Barbeiro> buscarPorTelefone(String telefone) {
        List<Barbeiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM barbeiros WHERE telefone LIKE ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + telefone + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro();
                barbeiro.setId(rs.getInt("id"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiro.setEspecialidade(rs.getString("especialidade"));
                barbeiro.setTelefone(rs.getString("telefone"));
                lista.add(barbeiro);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar barbeiro por telefone: " + e.getMessage());
        }

        return lista;
    }
}
