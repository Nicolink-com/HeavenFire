package senac.senacfx.application;

import com.sun.javafx.scene.control.skin.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            //macete para a barra de cima ficar ate o fim da tela
            scrollPane.setFitToHeight(true);

            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("HEAVENFIRE.COM");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Scene getMainScene(){
        return mainScene;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
//oi
