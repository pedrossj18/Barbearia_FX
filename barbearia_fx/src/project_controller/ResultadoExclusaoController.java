package project_controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import project_model.Cliente;
import project_dao.ClienteDAO;

import java.util.List;
import java.util.Optional;

public class ResultadoExclusaoController {

    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;

    private ObservableList<Cliente> clientesEncontrados;

    public void setClientes(List<Cliente> lista) {
        clientesEncontrados = FXCollections.observableArrayList(lista);
        tabelaClientes.setItems(clientesEncontrados);
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    private void excluirSelecionado() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Seleção obrigatória", "Selecione um cliente para excluir.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir o cliente selecionado?");
        confirmacao.setContentText("Cliente: " + selecionado.getNome());

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            ClienteDAO dao = new ClienteDAO();
            boolean sucesso = dao.excluir(selecionado.getId());

            if (sucesso) {
                clientesEncontrados.remove(selecionado);
                mostrarAlerta("Sucesso", "Cliente excluído com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Erro", "Erro ao excluir cliente.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_excluir_cliente.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Excluir Cliente");
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
