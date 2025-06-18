package project_controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project_model.Barbeiro;
import project_dao.BarbeiroDAO;

public class BarbeiroIncluirController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEspecialidade;
    @FXML private TextField txtTelefone;

    @FXML
    private void salvarBarbeiro() {
        String nome = txtNome.getText().trim();
        String especialidade = txtEspecialidade.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || especialidade.isEmpty() || telefone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Todos os campos devem ser preenchidos.");
            alert.showAndWait();
            return;
        }

        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome(nome);
        barbeiro.setEspecialidade(especialidade);
        barbeiro.setTelefone(telefone);

        BarbeiroDAO dao = new BarbeiroDAO();
        boolean sucesso = dao.inserir(barbeiro);

        Alert alerta = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alerta.setTitle("Cadastro de Barbeiro");
        alerta.setHeaderText(sucesso ? "Barbeiro cadastrado com sucesso!" : "Erro ao cadastrar barbeiro.");
        alerta.showAndWait();

        if (sucesso) {
            txtNome.clear();
            txtEspecialidade.clear();
            txtTelefone.clear();
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

            // Fecha a janela atual
            Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            atual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
