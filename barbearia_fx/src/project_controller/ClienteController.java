package project_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class ClienteController {

    @FXML
    private void incluirCliente(ActionEvent event) {
    	 try {
    	      Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_incluir_cliente.fxml"));
    	      Stage stage = new Stage();
    	      stage.setScene(new Scene(root));
    	      stage.setTitle("Incluir Cliente");
    	      stage.setResizable(false);
    	      stage.show();

    	      // Fecha a tela atual (opcional)
    	      Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	      atual.close();

    	  } catch (Exception e) {
    		  e.printStackTrace();
    	  }
    }

    @FXML
    private void excluirCliente(ActionEvent event) {
        // Aqui você pode abrir um diálogo para selecionar ou digitar o cliente a excluir
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_excluir_cliente.fxml"));
  	      	Stage stage = new Stage();
  	      	stage.setScene(new Scene(root));
  	      	stage.setTitle("Excluir Cliente");
  	      	stage.setResizable(false);
  	      	stage.show();
  	      
  	      	// Fecha a tela atual (opcional)
  	      	Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
  	      	atual.close();
  	      	
    	} catch (Exception e) {
  		  e.printStackTrace();
  	  }
    }

    @FXML
    private void listarClientes() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/lista_clientes.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Lista de Clientes");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void pesquisarAgendamentos() {
        System.out.println("Pesquisar agendamentos (a implementar)");
        // Você pode abrir outra janela ou mostrar uma caixa de busca
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_menu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.setResizable(false);
            stage.show();

            // Fecha a janela atual
            Stage janelaAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            janelaAtual.close();

            stage.setOnCloseRequest(ev -> {
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
        }
    }
}
