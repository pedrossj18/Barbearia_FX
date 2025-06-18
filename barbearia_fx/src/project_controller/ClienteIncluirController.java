package project_controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import project_model.Cliente;
import project_dao.ClienteDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClienteIncluirController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    @FXML
    private void salvarCliente() {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Todos os campos devem ser preenchidos.");
            alert.showAndWait();
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        ClienteDAO dao = new ClienteDAO();
        boolean sucesso = dao.inserir(cliente);

        Alert alerta = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alerta.setTitle("Cadastro de Cliente");
        alerta.setHeaderText(sucesso ? "Cliente cadastrado com sucesso!" : "Erro ao cadastrar cliente.");
        alerta.showAndWait();

        if (sucesso) {
            txtNome.clear();
            txtTelefone.clear();
            txtEmail.clear();
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_cliente.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Cliente");
            stage.setResizable(false);
            stage.show();

            Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            atual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aguarda a janela carregar para capturar o stage
        Platform.runLater(() -> {
            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.setOnCloseRequest((WindowEvent event) -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_cliente.fxml"));
                    Stage novoStage = new Stage();
                    novoStage.setScene(new Scene(root));
                    novoStage.setTitle("Menu Cliente");
                    novoStage.setResizable(false);
                    novoStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
