package project_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class view extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/project_view/tela_login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login - Barbearia");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // mostra erro se o FXML não for carregado
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
