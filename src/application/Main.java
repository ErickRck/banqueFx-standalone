package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../com/views/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/Styles.css");
        Image icon = new Image("com/views/icons/LoginPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
       // Media someSound = new Media(getClass().getResource("/audio/Welcome.mp3").toString());
       // MediaPlayer mp = new MediaPlayer(someSound);
       // mp.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
