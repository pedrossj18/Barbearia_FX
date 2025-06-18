package project_controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class MenuController {

    @FXML
    private void abrirAgendamento(ActionEvent event) {
        abrirTela("/project_view/tela_agendamento.fxml", "Agendamento", event);
    }

    @FXML
    private void abrirBarbeiro(ActionEvent event) {
        abrirTela("/project_view/tela_barbeiro.fxml", "Barbeiro", event);
    }

    @FXML
    private void abrirCliente(ActionEvent event) {
        abrirTela("/project_view/tela_cliente.fxml", "Cliente", event);
    }

    @FXML
    private void sair() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação de Saída");
        alerta.setHeaderText("Deseja realmente sair do sistema?");
        alerta.setContentText("Clique em OK para sair ou Cancelar para continuar.");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void abrirTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent tela = loader.load(); // renomeado de 'root' para 'tela'

            Stage novaJanela = new Stage();
            novaJanela.setTitle("Barbearia - " + titulo);
            novaJanela.setScene(new Scene(tela));
            novaJanela.setResizable(false);
            novaJanela.show();

            // Fecha a janela atual (menu)
            Stage janelaAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janelaAtual.close();

            // Ao fechar com "X", volta para o menu
            novaJanela.setOnCloseRequest(evento -> {
                try {
                    Parent menuRoot = FXMLLoader.load(getClass().getResource("/project_view/tela_menu.fxml"));
                    Stage menuStage = new Stage();
                    menuStage.setScene(new Scene(menuRoot));
                    menuStage.setTitle("Menu Principal");
                    menuStage.setResizable(false);
                    menuStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Falha ao abrir " + titulo);
            erro.setContentText(e.getMessage());
            erro.showAndWait();
        }
    }
}
