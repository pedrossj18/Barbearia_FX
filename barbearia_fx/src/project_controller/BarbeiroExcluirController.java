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

import project_dao.BarbeiroDAO;
import project_model.Barbeiro;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BarbeiroExcluirController implements Initializable {

    @FXML private TextField txtIdBarbeiro;
    @FXML private TextField txtNomeBarbeiro;
    @FXML private TextField txtTelefoneBarbeiro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Stage stage = (Stage) txtIdBarbeiro.getScene().getWindow();
            stage.setOnCloseRequest((WindowEvent event) -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_barbeiro.fxml"));
                    Stage novoStage = new Stage();
                    novoStage.setScene(new Scene(root));
                    novoStage.setTitle("Menu Barbeiro");
                    novoStage.setResizable(false);
                    novoStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @FXML
    private void excluirBarbeiro(ActionEvent event) {
        String idTexto = txtIdBarbeiro.getText().trim();
        String nome = txtNomeBarbeiro.getText().trim();
        String telefone = txtTelefoneBarbeiro.getText().trim();

        if (idTexto.isEmpty() && nome.isEmpty() && telefone.isEmpty()) {
            mostrarAlerta("Erro", "Preencha pelo menos um campo para pesquisar.", Alert.AlertType.WARNING);
            return;
        }

        BarbeiroDAO dao = new BarbeiroDAO();
        List<Barbeiro> resultados = null;

        if (!idTexto.isEmpty()) {
            try {
                int id = Integer.parseInt(idTexto);
                Barbeiro barbeiro = dao.buscarPorId(id);
                if (barbeiro != null) {
                    resultados = List.of(barbeiro);
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
            mostrarAlerta("Nenhum resultado", "Nenhum barbeiro encontrado com os dados informados.", Alert.AlertType.INFORMATION);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/tela_resultado_exclusao_barbeiro.fxml"));
            Parent root = loader.load();

            ResultadoExclusaoBarbeiroController controller = loader.getController();
            controller.setBarbeiros(resultados);

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
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_barbeiro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Barbeiro");
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
