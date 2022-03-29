package mvh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Main extends Application {

    public static final String version = "1.0";

    /**
     * A program-wide random number generator
     */
    public static Random random = new Random(12345);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        //Students edit here to set up the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Inconsolata");
        stage.setScene(scene);
        stage.setTitle("MvH World Editor 1.0");
        stage.show();
    }
}
