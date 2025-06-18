package project_dao;

import project_model.Agendamento;
import project_model.Barbeiro;
import project_model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

	public boolean inserir(Agendamento agendamento) {
	    String sql = "INSERT INTO agendamentos (cliente_id, barbeiro_id, data_hora, servico, observacoes) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = Conexao.getConexao();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, agendamento.getCliente().getId());
	        stmt.setInt(2, agendamento.getBarbeiro().getId());
	        stmt.setTimestamp(3, Timestamp.valueOf(agendamento.getDataHora()));
	        stmt.setString(4, agendamento.getServico());
	        stmt.setString(5, agendamento.getObservacoes());

	        int linhasAfetadas = stmt.executeUpdate();
	        return linhasAfetadas > 0;

	    } catch (SQLException e) {
	        System.err.println("Erro ao inserir agendamento: " + e.getMessage());
	        return false;
	    }
	}

    public List<Agendamento> listar() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id AS agendamento_id, " +
                     "c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "b.id AS barbeiro_id, b.nome AS barbeiro_nome, " +
                     "a.data_hora, a.servico, a.observacoes " +
                     "FROM agendamentos a " +
                     "JOIN clientes c ON a.cliente_id = c.id " +
                     "JOIN barbeiros b ON a.barbeiro_id = b.id";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(rs.getInt("agendamento_id"));
                agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                agendamento.setServico(rs.getString("servico"));
                agendamento.setObservacoes(rs.getString("observacoes"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                agendamento.setCliente(cliente);
                agendamento.setClienteNome(cliente.getNome());

                Barbeiro barbeiro = new Barbeiro();
                barbeiro.setId(rs.getInt("barbeiro_id"));
                barbeiro.setNome(rs.getString("barbeiro_nome"));
                agendamento.setBarbeiro(barbeiro);
                agendamento.setBarbeiroNome(barbeiro.getNome());

                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeAgendamentoPorId(int id) {
        String sql = "SELECT COUNT(*) FROM agendamentos WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Agendamento> pesquisarPorNomeCliente(String nome) {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id AS agendamento_id, c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "b.id AS barbeiro_id, b.nome AS barbeiro_nome, " +
                     "a.data_hora, a.servico, a.observacoes " +
                     "FROM agendamentos a " +
                     "JOIN clientes c ON a.cliente_id = c.id " +
                     "JOIN barbeiros b ON a.barbeiro_id = b.id " +
                     "WHERE c.nome LIKE ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(rs.getInt("agendamento_id"));
                    agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    agendamento.setServico(rs.getString("servico"));
                    agendamento.setObservacoes(rs.getString("observacoes"));

                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                    agendamento.setCliente(cliente);
                    agendamento.setClienteNome(cliente.getNome());

                    Barbeiro barbeiro = new Barbeiro();
                    barbeiro.setId(rs.getInt("barbeiro_id"));
                    barbeiro.setNome(rs.getString("barbeiro_nome"));
                    agendamento.setBarbeiro(barbeiro);
                    agendamento.setBarbeiroNome(barbeiro.getNome());

                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }

    public List<Agendamento> pesquisarPorNomeBarbeiro(String nome) {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id AS agendamento_id, c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "b.id AS barbeiro_id, b.nome AS barbeiro_nome, " +
                     "a.data_hora, a.servico, a.observacoes " +
                     "FROM agendamentos a " +
                     "JOIN clientes c ON a.cliente_id = c.id " +
                     "JOIN barbeiros b ON a.barbeiro_id = b.id " +
                     "WHERE b.nome LIKE ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(rs.getInt("agendamento_id"));
                    agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    agendamento.setServico(rs.getString("servico"));
                    agendamento.setObservacoes(rs.getString("observacoes"));

                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                    agendamento.setCliente(cliente);
                    agendamento.setClienteNome(cliente.getNome());

                    Barbeiro barbeiro = new Barbeiro();
                    barbeiro.setId(rs.getInt("barbeiro_id"));
                    barbeiro.setNome(rs.getString("barbeiro_nome"));
                    agendamento.setBarbeiro(barbeiro);
                    agendamento.setBarbeiroNome(barbeiro.getNome());

                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }

    public List<Agendamento> listarPorIdBarbeiro(int idBarbeiro) {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.id AS agendamento_id, c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "b.id AS barbeiro_id, b.nome AS barbeiro_nome, " +
                     "a.data_hora, a.servico, a.observacoes " +
                     "FROM agendamentos a " +
                     "JOIN clientes c ON a.cliente_id = c.id " +
                     "JOIN barbeiros b ON a.barbeiro_id = b.id " +
                     "WHERE b.id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBarbeiro);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(rs.getInt("agendamento_id"));
                    agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    agendamento.setServico(rs.getString("servico"));
                    agendamento.setObservacoes(rs.getString("observacoes"));

                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                    agendamento.setCliente(cliente);
                    agendamento.setClienteNome(cliente.getNome());

                    Barbeiro barbeiro = new Barbeiro();
                    barbeiro.setId(rs.getInt("barbeiro_id"));
                    barbeiro.setNome(rs.getString("barbeiro_nome"));
                    agendamento.setBarbeiro(barbeiro);
                    agendamento.setBarbeiroNome(barbeiro.getNome());

                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }
} 
