package mvh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/*
  Name: Faisal Islam
  Date: March 29, 2022
  Tutorial: T10- Tejash
 */

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

        //Add Google font "Inconsolata" as a stylesheet to use monospaced font universally across Operating Systems
        //so that the WorldView looks neat and consistent
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Inconsolata");
        stage.setScene(scene);
        //Set Window Title and add Application Icon
        stage.setTitle("MvH World Editor 1.0");
        stage.getIcons().add(new Image("/rupee.png"));
        stage.show();
    }
}
