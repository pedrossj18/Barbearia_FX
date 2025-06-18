package project_controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import project_model.Agendamento;
import project_model.Cliente;
import project_model.Barbeiro;
import project_dao.AgendamentoDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class AgendamentoIncluirController {
	
	 @FXML private TextField txtClienteId;
	    @FXML private TextField txtBarbeiroId;
	    @FXML private TextField txtData;
	    @FXML private TextField txtHora;
	    @FXML private TextField txtServico;
	    @FXML private TextArea txtObservacoes;

	    @FXML
	    private void salvarAgendamento(ActionEvent event) {
	        try {
	            int clienteId = Integer.parseInt(txtClienteId.getText());
	            int barbeiroId = Integer.parseInt(txtBarbeiroId.getText());
	            LocalDate data = LocalDate.parse(txtData.getText());
	            LocalTime hora = LocalTime.parse(txtHora.getText());

	            Agendamento agendamento = new Agendamento();
	            agendamento.setCliente(new Cliente(clienteId));
	            agendamento.setBarbeiro(new Barbeiro(barbeiroId));
	            agendamento.setDataHora(LocalDateTime.of(data, hora));
	            agendamento.setServico(txtServico.getText());
	            agendamento.setObservacoes(txtObservacoes.getText());

	            AgendamentoDAO dao = new AgendamentoDAO();
	            boolean sucesso = dao.inserir(agendamento);

	            Alert alert = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
	            alert.setTitle("Agendamento");
	            alert.setHeaderText(sucesso ? "Agendamento salvo com sucesso!" : "Erro ao salvar agendamento.");
	            alert.showAndWait();

	            if (sucesso) {
	                txtClienteId.clear();
	                txtBarbeiroId.clear();
	                txtData.clear();
	                txtHora.clear();
	                txtServico.clear();
	                txtObservacoes.clear();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erro");
	            alert.setHeaderText("Erro ao salvar agendamento.");
	            alert.setContentText("Verifique os campos informados.");
	            alert.showAndWait();
	        }
	    }
	    
	    @FXML
	    private void abrirListaClientes(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/lista_clientes.fxml"));
	            Parent root = loader.load();

	            Stage stage = new Stage();
	            stage.setTitle("Lista de Clientes");
	            stage.setScene(new Scene(root));
	            stage.setResizable(false);
	            stage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	            mostrarAlerta("Erro", "Não foi possível abrir a lista de clientes.");
	        }
	    }

	    @FXML
	    private void abrirListaBarbeiros(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project_view/tela_listar_barbeiros.fxml"));
	            Parent root = loader.load();

	            Stage stage = new Stage();
	            stage.setTitle("Lista de Barbeiros");
	            stage.setScene(new Scene(root));
	            stage.setResizable(false);
	            stage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	            mostrarAlerta("Erro", "Não foi possível abrir a lista de barbeiros.");
	        }
	    }

	    private void mostrarAlerta(String titulo, String mensagem) {
	        Alert alerta = new Alert(Alert.AlertType.ERROR);
	        alerta.setTitle(titulo);
	        alerta.setHeaderText(null);
	        alerta.setContentText(mensagem);
	        alerta.showAndWait();
	    }

	    @FXML
	    private void voltar(ActionEvent event) {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_agendamento.fxml"));
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setTitle("Menu Agendamento");
	            stage.setResizable(false);
	            stage.show();
	            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
