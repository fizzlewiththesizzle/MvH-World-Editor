package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;
import java.util.Objects;

public class MainController {

    //Store the data of editor
    private World world;

    //Getter and Setter Methods
    public World getWorld(){
        return this.world;
    }

    public void setWorld(World world){
        this.world = world;
    }

    /**
     * Setup the window state
     */
    @FXML
    private TextField columnField;

    @FXML
    private TextField rowField;

    @FXML
    private TextField heroArmor;

    @FXML
    private TextField heroColumn;

    @FXML
    private TextField heroHealth;

    @FXML
    private TextField heroRow;

    @FXML
    private TextField heroSymbol;

    @FXML
    private TextField heroWeapon;

    @FXML
    private Label leftStatus;

    @FXML
    private TextField monsterColumn;

    @FXML
    private TextField monsterHealth;

    @FXML
    private TextField monsterRow;

    @FXML
    private TextField monsterSymbol;

    @FXML
    private TextArea worldView;

    @FXML
    private ChoiceBox<String> monsterWeapon;

    @FXML
    public void initialize() {
        monsterWeapon.setValue("Select Weapon");
        monsterWeapon.getItems().add("Club(2)");
        monsterWeapon.getItems().add("Axe(3)");
        monsterWeapon.getItems().add("Sword(4)");
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
    void addHero(ActionEvent event) {
        try{
            int row = Integer.parseInt(heroRow.getText());
            int column = Integer.parseInt(heroColumn.getText());
            int armorStrength = Integer.parseInt(heroArmor.getText());
            int health = Integer.parseInt(heroHealth.getText());
            int weaponStrength = Integer.parseInt(heroWeapon.getText());
            char symbol = heroSymbol.getText().charAt(0);

            Hero hero = new Hero(health, symbol, weaponStrength,armorStrength);
            getWorld().addEntity(row, column, hero);
            String hero_status = "Added Hero at (" + row + "," + column + ")!";
            System.out.println("\n" + hero_status);
            leftStatus.setStyle(null);
            leftStatus.setText(hero_status);

            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
        } catch (NumberFormatException e) {
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }

    }

    @FXML
    void addMonster(ActionEvent event) {
        try{
            int row = Integer.parseInt(monsterRow.getText());
            int column = Integer.parseInt(monsterColumn.getText());
            int health = Integer.parseInt(monsterHealth.getText());
            char weapon = monsterWeapon.getValue().charAt(0);
            WeaponType weapontype = WeaponType.getWeaponType(weapon);
            char symbol = monsterSymbol.getText().charAt(0);

            Monster monster = new Monster(health, symbol, weapontype);
            getWorld().addEntity(row, column, monster);
            String monsterStatus = "Added Monster at (" + row + "," + column + ")!";
            System.out.println("\n" + monsterStatus);
            leftStatus.setStyle(null);
            leftStatus.setText(monsterStatus);

            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
        } catch (NumberFormatException e) {
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }

    @FXML
    void createWorld(ActionEvent event) {
        try{
            int row = Integer.parseInt(rowField.getText());
            int column = Integer.parseInt(columnField.getText());
            System.out.println(row + " rows || " + column + " columns");
            World world = new World(row, column);
            setWorld(world);
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
            leftStatus.setStyle(null);
            leftStatus.setText("New " + row + "x" + column + " world created!");
        } catch (NumberFormatException e) {
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }

    @FXML
    void loadFile(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("world.txt");
        File fileOpen = fileChooser.showOpenDialog(new Stage());
        System.out.println(fileOpen);
        World world = Reader.loadWorld(fileOpen);
        setWorld(world);
        String worldString = getWorld().worldString();
        System.out.println(worldString);
        worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
        worldView.setText(worldString);
    }

    @FXML
    void quitProgram(ActionEvent event) {
        System.exit(0);
    }

}
