package project_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import project_dao.ClienteDAO;
import project_model.Cliente;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteExcluirController implements Initializable {

    @FXML private TextField txtIdCliente;
    @FXML private TextField txtNomeCliente;
    @FXML private TextField txtTelefoneCliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Adiciona o comportamento de reabrir o menu ao fechar com o X
        Platform.runLater(() -> {
            Stage stage = (Stage) txtIdCliente.getScene().getWindow();
            stage.setOnCloseRequest((WindowEvent event) -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_cliente.fxml"));
                    Stage novoStage = new Stage();
                    novoStage.setScene(new Scene(root));
                    novoStage.setTitle("Menu Cliente");
                    novoStage.setResizable(false);
                    novoStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    private void excluirCliente(ActionEvent event) {
        String idTexto = txtIdCliente.getText().trim();
        String nome = txtNomeCliente.getText().trim();
        String telefone = txtTelefoneCliente.getText().trim();

        if (idTexto.isEmpty() && nome.isEmpty() && telefone.isEmpty()) {
            mostrarAlerta("Erro", "Preencha pelo menos um campo para pesquisar.", Alert.AlertType.WARNING);
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        List<Cliente> resultados = null;

        if (!idTexto.isEmpty()) {
            try {
                int id = Integer.parseInt(idTexto);
                Cliente cliente = dao.buscarPorId(id);
                if (cliente != null) {
                    resultados = List.of(cliente);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "ID inválido. Digite apenas números.", Alert.AlertType.ERROR);
                return;
            }
        } else if (!nome.isEmpty()) {
            resultados = dao.buscarPorNome(nome);
        } else if (!telefone.isEmpty()) {
            resultados = dao.buscarPorTelefone(telefone);
        }

        if (resultados == null || resultados.isEmpty()) {
            mostrarAlerta("Nenhum resultado", "Nenhum cliente encontrado com os dados informados.", Alert.AlertType.INFORMATION);
            return;
        }

        // abrir nova janela com resultados (tela_resultado_exclusao.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/tela_resultado_exclusao.fxml"));
            Parent root = loader.load();

            ResultadoExclusaoController controller = loader.getController();
            controller.setClientes(resultados);

            Stage stage = new Stage();
            stage.setTitle("Resultados da Pesquisa");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao carregar a tela de resultados.", Alert.AlertType.ERROR);
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

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
