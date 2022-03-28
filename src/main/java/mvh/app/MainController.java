package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import mvh.world.*;

public class MainController {

    //Store the data of editor
    private World world;

    /**
     * Setup the window state
     */
    @FXML
    public void initialize() {
    }

    @FXML
    void aboutProgram(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION,"test");
        String info_txt = "Author: Faisal Islam\nEmail: faisal.islam@ucalgary.ca\nVersion: 1.0\nA GUI interface to create and edit MvH worlds";
        info.setContentText(info_txt);
        info.setHeaderText("About");
        info.setTitle("About Program");
        info.show();
    }

    @FXML
    void quitProgram(ActionEvent event) {
        System.exit(0);
    }

}
