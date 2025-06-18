package project_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import project_model.Barbeiro;
import project_dao.BarbeiroDAO;

import java.util.List;
import java.util.Optional;

public class ResultadoExclusaoBarbeiroController {

    @FXML private TableView<Barbeiro> tableBarbeiros;
    @FXML private TableColumn<Barbeiro, Integer> colId;
    @FXML private TableColumn<Barbeiro, String> colNome;
    @FXML private TableColumn<Barbeiro, String> colEspecialidade;
    @FXML private TableColumn<Barbeiro, String> colTelefone;

    private ObservableList<Barbeiro> listaBarbeiros;

    public void setBarbeiros(List<Barbeiro> barbeiros) {
        listaBarbeiros = FXCollections.observableArrayList(barbeiros);
        tableBarbeiros.setItems(listaBarbeiros);
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colEspecialidade.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEspecialidade()));
        colTelefone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefone()));
    }

    @FXML
    private void excluirSelecionado(ActionEvent event) {
        Barbeiro barbeiro = tableBarbeiros.getSelectionModel().getSelectedItem();

        if (barbeiro == null) {
            mostrarAlerta("Aviso", "Selecione um barbeiro para excluir.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir o barbeiro?");
        confirmacao.setContentText("Nome: " + barbeiro.getNome());

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            BarbeiroDAO dao = new BarbeiroDAO();
            boolean sucesso = dao.excluir(barbeiro.getId());

            if (sucesso) {
                listaBarbeiros.remove(barbeiro);
                mostrarAlerta("Sucesso", "Barbeiro excluído com sucesso.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Erro", "Erro ao excluir barbeiro.", Alert.AlertType.ERROR);
            }
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
