package project_dao;

//Importações necessárias para conexão com banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	// URL de conexão com o banco de dados MySQL (ajuste a porta se necessário)
    private static final String URL = "jdbc:mysql://localhost:3306/barbearia_db";
    private static final String USUARIO = "root";
    private static final String SENHA = "Gavioes*120196"; 

    public static Connection getConexao() {
        try {
        	// Tenta estabelecer a conexão usando DriverManager
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
            return null;// Retorna null se a conexão falhar
        }
    }
}
