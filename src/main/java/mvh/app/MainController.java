package mvh.app;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
    private Label rightStatus;

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
    private ListView<String> statList;

    @FXML
    private TextField removeColumn;

    @FXML
    private TextField removeRow;

    @FXML
    public void initialize() {
        monsterWeapon.setValue("Select Weapon");
        monsterWeapon.getItems().add("Club(2)");
        monsterWeapon.getItems().add("Axe(3)");
        monsterWeapon.getItems().add("Sword(4)");
        statList.getItems().add("Create an Entity");
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

            statList.getItems().clear();
            statList.getItems().add("Type: Hero");
            statList.getItems().add("Symbol: " + symbol);
            statList.getItems().add("Health: " + health);
            statList.getItems().add("Weapon Strength: " + weaponStrength);
            statList.getItems().add("Armor Strength: " + armorStrength);

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

            statList.getItems().clear();
            statList.getItems().add("Type: Monster");
            statList.getItems().add("Symbol: " + symbol);
            statList.getItems().add("Health: " + health);
            statList.getItems().add("Weapon: " + weapontype);
            statList.getItems().add("Armor Strength: " + weapontype.getWeaponStrength());

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
            rightStatus.setText("World created!");
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
        rightStatus.setText("World loaded!");
    }

    @FXML
    void quitProgram(ActionEvent event) {
        System.exit(0);
    }

    public void heroSelect(Event event) {
        if(leftStatus != null){
            leftStatus.setStyle(null);
            leftStatus.setText("Currently adding heroes");
        }
    }

    public void monsterSelect(Event event) {
        leftStatus.setStyle(null);
        leftStatus.setText("Currently adding monsters");
    }

    public void removeSelect(Event event) {
        leftStatus.setStyle(null);
        leftStatus.setText("Currently removing entities");
    }

    public void removeEntity(ActionEvent actionEvent) {
        try{
            int row = Integer.parseInt(removeRow.getText());
            int column = Integer.parseInt(removeColumn.getText());
            Entity entity = getWorld().getEntity(row, column);
            String type = "";

            if(entity instanceof Hero){
                type = "Hero";
            }
            if(entity instanceof Monster){
                type = "Monster";
            }

            getWorld().addEntity(row, column, null);
            String removeStatus = "Removed " + type + " at (" + row + "," + column + ")!";
            System.out.println("\n" + removeStatus);
            leftStatus.setStyle(null);
            leftStatus.setText(removeStatus);

            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);

        } catch (NumberFormatException e) {
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }

    public void saveFile(ActionEvent actionEvent) throws FileNotFoundException {
        StringBuilder fileBuild = new StringBuilder();
        fileBuild.append(getWorld().getRows()).append("\n").append(getWorld().getColumns());

        for(int row = 0; row <= getWorld().getRows() - 1; row++){
            for(int column = 0; column <= getWorld().getColumns() - 1; column++){
                if(getWorld().getEntity(row, column) instanceof Hero){
                    Entity hero = getWorld().getEntity(row, column);
                    fileBuild.append("\n").append(row).append(",").append(column).append(",").append("HERO").append(",").append(hero.getSymbol()).append(",").append(hero.getHealth()).append(",").append(hero.weaponStrength()).append(",").append(hero.armorStrength());
                }
                else if(getWorld().getEntity(row, column) instanceof Monster){
                    Entity monster = getWorld().getEntity(row, column);
                    char weapon = 0;
                    if(monster.weaponStrength() == 2){
                        weapon = 'C';
                    }
                    else if(monster.weaponStrength() == 3){
                        weapon = 'A';
                    }
                    else if(monster.weaponStrength() == 4){
                        weapon = 'S';
                    }
                    fileBuild.append("\n").append(row).append(",").append(column).append(",").append("MONSTER").append(",").append(monster.getSymbol()).append(",").append(monster.getHealth()).append(",").append(weapon);
                }
                else{
                    fileBuild.append("\n").append(row).append(",").append(column);
                }
            }
        }
        String fileString = fileBuild.toString();
        System.out.println("\n" + fileString);

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("world.txt");
        File fileSave = fileChooser.showSaveDialog(new Stage());

        if(fileSave != null){
            PrintWriter pw = new PrintWriter(fileSave);
            pw.print(fileString);
            pw.close();
        }
        System.out.println(fileSave);
    }
}
