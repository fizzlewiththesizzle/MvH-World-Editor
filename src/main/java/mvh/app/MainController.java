package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;

public class MainController {

    //Store the data of editor
    private World world;

    /**
     * Setup the window state
     */
    @FXML
    private TextField columnField;

    @FXML
    private TextField rowField;

    @FXML
    private TextArea worldView;

    @FXML
    private ChoiceBox<String> entityBox;

    @FXML
    public void initialize() {
        entityBox.getItems().add("Monster");
        entityBox.getItems().add("Hero");
    }

    @FXML
    void aboutProgram(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        String info_txt = "Author: Faisal Islam\nEmail: faisal.islam@ucalgary.ca\nVersion: 1.0\nA GUI interface to create and edit MvH worlds";
        info.setContentText(info_txt);
        info.setHeaderText("About");
        info.setTitle("About Program");
        info.show();
    }

    @FXML
    void createWorld(ActionEvent event) {
        int row = Integer.parseInt(rowField.getText());
        int column = Integer.parseInt(columnField.getText());
        System.out.println(row + " rows || " + column + " columns");
        World world = new World(row, column);
        String worldString = world.worldString();
        System.out.println(worldString);
        worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
        worldView.setText(worldString);
    }

    @FXML
    void loadFile(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("world.txt");
        File fileOpen = fileChooser.showOpenDialog(new Stage());
        System.out.println(fileOpen);
        World world = Reader.loadWorld(fileOpen);
        String worldString = world.worldString();
        System.out.println(worldString);
        worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
        worldView.setText(worldString);
    }

    @FXML
    void quitProgram(ActionEvent event) {
        System.exit(0);
    }

}
