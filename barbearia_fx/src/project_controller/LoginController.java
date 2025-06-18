package project_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import project_dao.UsuarioDAO;
import project_model.Usuario;

public class LoginController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;

    @FXML
    private void entrar() {
        String login = campoUsuario.getText();
        String senha = campoSenha.getText();

        // Autentica no banco de dados usando UsuarioDAO
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(login, senha);

        if (usuario != null) {
            // Login bem-sucedido
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/tela_menu.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Menu Principal - Barbearia");
                stage.setScene(new Scene(root));
                stage.setResizable(false); // impede maximizar
                stage.show();

                // Fecha a tela de login atual
                Stage loginStage = (Stage) campoUsuario.getScene().getWindow();
                loginStage.close();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao carregar o menu");
                alert.setContentText("Verifique o arquivo tela_menu.fxml ou o controller.");
                alert.showAndWait();
            }

        } else {
            // Login falhou
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Credenciais inválidas");
            alert.setContentText("Usuário ou senha incorretos.");
            alert.showAndWait();
        }
    }
}
