package project_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class BarbeiroController {

    @FXML
    private void incluirBarbeiro(ActionEvent event) {
        abrirNovaTela("/project_view/tela_incluir_barbeiro.fxml", "Incluir Barbeiro", event);
    }

    @FXML
    private void excluirBarbeiro(ActionEvent event) {
        abrirNovaTela("/project_view/tela_excluir_barbeiro.fxml", "Excluir Barbeiro", event);
    }

    @FXML
    private void listarBarbeiros(ActionEvent event) {
        abrirNovaTela("/project_view/tela_listar_barbeiros.fxml", "Listar Barbeiros", event);
    }

    @FXML
    private void listarAgendamentosBarbeiros(ActionEvent event) {
        abrirNovaTela("/project_view/tela_agendamentos_barbeiro.fxml", "Agendamentos dos Barbeiros", event);
    }

    @FXML
    private void voltar(ActionEvent event) {
        abrirNovaTela("/project_view/tela_menu.fxml", "Menu Principal", event);
    }

    private void abrirNovaTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Stage novaJanela = new Stage();
            novaJanela.setTitle(titulo);
            novaJanela.setScene(new Scene(root));
            novaJanela.setResizable(false);
            novaJanela.show();

            // Fecha a janela atual
            Stage janelaAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janelaAtual.close();

            // Reabre a tela de Barbeiros ao fechar a nova janela
            novaJanela.setOnCloseRequest(ev -> {
                try {
                    Parent menuRoot = FXMLLoader.load(getClass().getResource("/project_view/tela_barbeiro.fxml"));
                    Stage menuStage = new Stage();
                    menuStage.setScene(new Scene(menuRoot));
                    menuStage.setTitle("Tela de Barbeiros");
                    menuStage.setResizable(false);
                    menuStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
