package project_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import project_model.Agendamento;
import project_dao.AgendamentoDAO;

import java.util.List;

public class ListarAgendamentosController {

    @FXML private TableView<Agendamento> tabelaAgendamentos;
    @FXML private TableColumn<Agendamento, Integer> colId;
    @FXML private TableColumn<Agendamento, String> colNomeCliente;
    @FXML private TableColumn<Agendamento, String> colNomeBarbeiro;
    @FXML private TableColumn<Agendamento, String> colData;
    @FXML private TableColumn<Agendamento, String> colServico;
    @FXML private TableColumn<Agendamento, String> colObservacoes;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colNomeBarbeiro.setCellValueFactory(new PropertyValueFactory<>("barbeiroNome"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        colObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        carregarAgendamentos();
    }

    private void carregarAgendamentos() {
        AgendamentoDAO dao = new AgendamentoDAO();
        List<Agendamento> lista = dao.listar();
        ObservableList<Agendamento> observableList = FXCollections.observableArrayList(lista);
        tabelaAgendamentos.setItems(observableList);
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_agendamento.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Agendamentos");
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
