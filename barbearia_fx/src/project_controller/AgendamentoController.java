package project_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class AgendamentoController {

    @FXML
    private void incluirAgendamento(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/tela_incluir_agendamento.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Incluir Agendamento");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            // Fecha a tela atual, se necessário
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void excluirAgendamento(ActionEvent event) {
        abrirTela("/project_view/tela_excluir_agendamento.fxml", "Excluir Agendamento", event);
    }

    @FXML
    private void listarAgendamentos(ActionEvent event) {
        abrirTela("/project_view/tela_listar_agendamentos.fxml", "Listar Agendamentos", event);
    }

    @FXML
    private void pesquisarPorCliente(ActionEvent event) {
        abrirTela("/project_view/tela_pesquisar_cliente_agendamento.fxml", "Pesquisar por Cliente", event);
    }

    @FXML
    private void pesquisarPorBarbeiro(ActionEvent event) {
        abrirTela("/project_view/tela_pesquisar_barbeiro_agendamento.fxml", "Pesquisar por Barbeiro", event);
    }

    @FXML
    private void voltar(ActionEvent event) {
        abrirTela("/project_view/tela_menu.fxml", "Menu Principal", event);
    }

    private void abrirTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}