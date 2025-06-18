package project_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project_model.Barbeiro;
import project_dao.BarbeiroDAO;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.util.List;

public class BarbeiroListaController {

    @FXML private TableView<Barbeiro> tabelaBarbeiros;
    @FXML private TableColumn<Barbeiro, Integer> colId;
    @FXML private TableColumn<Barbeiro, String> colNome;
    @FXML private TableColumn<Barbeiro, String> colEspecialidade;
    @FXML private TableColumn<Barbeiro, String> colTelefone;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarBarbeiros();
    }

    private void carregarBarbeiros() {
        BarbeiroDAO dao = new BarbeiroDAO();
        List<Barbeiro> lista = dao.listar();
        ObservableList<Barbeiro> observableList = FXCollections.observableArrayList(lista);
        tabelaBarbeiros.setItems(observableList);
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_barbeiro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Barbeiros");
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
