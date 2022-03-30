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

/*
  Name: Faisal Islam
  Date: March 29, 2022
  Tutorial: T10- Tejash
 */

public class MainController {

    //Store the data of editor
    private World world;

    //Getter and Setter Methods for the World being loaded/created/edited
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
    private TextField viewColumn;

    @FXML
    private TextField viewRow;

    /**
     * Function that runs code to set certain values pertaining to the GUI when program is initially run
     */
    @FXML
    public void initialize() {
        //Set initial value of dropdown for user's ease of access, then add the selectable items
        monsterWeapon.setValue("Select Weapon");
        monsterWeapon.getItems().add("Club(2)");
        monsterWeapon.getItems().add("Axe(3)");
        monsterWeapon.getItems().add("Sword(4)");
        //Set initial value to Stats view for user's ease of access
        statList.getItems().add("Create or Select an Entity");
    }

    /**
     * Function that displays an Alert containing information about the author and the program
     * @param event When "About" menu option is selected from "Help" menu
     */
    @FXML
    void aboutProgram(ActionEvent event) {
        //Initialize Alert and set its attributes
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        String info_txt = "Author: Faisal Islam\nEmail: faisal.islam@ucalgary.ca\nVersion: 1.0\nA GUI interface to create and edit MvH worlds";
        info.setContentText(info_txt);
        info.setHeaderText("About");
        info.setTitle("About Program");
        info.show();
    }

    /**
     * Function that adds a Hero entity to the world based on the information provided by the user in the GUI
     * Additionally updates left status area to provide useful information about the newly added Hero, as well as
     * displaying the Hero's stats in the Stats view
     * @param event When "Add Hero" button is clicked
     */
    @FXML
    void addHero(ActionEvent event) {
        try{
            //Assign user input to variables
            int row = Integer.parseInt(heroRow.getText());
            int column = Integer.parseInt(heroColumn.getText());
            int armorStrength = Integer.parseInt(heroArmor.getText());
            int health = Integer.parseInt(heroHealth.getText());
            int weaponStrength = Integer.parseInt(heroWeapon.getText());
            char symbol = heroSymbol.getText().charAt(0);

            //Use parsed data to create a Hero entity and add them to the World
            Hero hero = new Hero(health, symbol, weaponStrength,armorStrength);
            getWorld().addEntity(row, column, hero);
            //Print and set messages for Hero being added to World
            String hero_status = "Added Hero at (" + row + "," + column + ")!";
            System.out.println("\n" + hero_status);
            leftStatus.setStyle(null);
            leftStatus.setText(hero_status);

            //Add newly added Hero's stats to Stats view
            statList.getItems().clear();
            statList.getItems().add("Type: Hero");
            statList.getItems().add("Symbol: " + symbol);
            statList.getItems().add("Health: " + health);
            statList.getItems().add("Weapon Strength: " + weaponStrength);
            statList.getItems().add("Armor Strength: " + armorStrength);

            //Update, print, and display updated World
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
        } catch (NumberFormatException e) {
            //Catch invalid input and display error message to user
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
        catch(NullPointerException e){
            //Catch if user tries to add entity without creating or loading a World first
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Load or create a world first!");
        }
    }

    /**
     * Function that adds a Monster entity to the world based on the information provided by the user in the GUI
     * Additionally updates left status area to provide useful information about the newly added Monster, as well as
     * displaying the Monster's stats in the Stats view
     * @param event When "Add Monster" button is clicked
     */
    @FXML
    void addMonster(ActionEvent event) {
        try{
            //Assign user input to variables
            int row = Integer.parseInt(monsterRow.getText());
            int column = Integer.parseInt(monsterColumn.getText());
            int health = Integer.parseInt(monsterHealth.getText());
            char weapon = monsterWeapon.getValue().charAt(0);
            WeaponType weapontype = WeaponType.getWeaponType(weapon);
            char symbol = monsterSymbol.getText().charAt(0);

            //Use parsed data to create a Monster entity and add them to the World
            Monster monster = new Monster(health, symbol, weapontype);
            getWorld().addEntity(row, column, monster);
            //Print and set messages for Hero being added to World
            String monsterStatus = "Added Monster at (" + row + "," + column + ")!";
            System.out.println("\n" + monsterStatus);
            leftStatus.setStyle(null);
            leftStatus.setText(monsterStatus);

            //Add newly added Monster's stats to Stats view
            statList.getItems().clear();
            statList.getItems().add("Type: Monster");
            statList.getItems().add("Symbol: " + symbol);
            statList.getItems().add("Health: " + health);
            statList.getItems().add("Weapon: " + weapontype);
            statList.getItems().add("Weapon Strength: " + weapontype.getWeaponStrength());

            //Update, print, and display updated World
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
        } catch (NumberFormatException e) {
            //Catch invalid input and display error message to user
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
        catch(NullPointerException e){
            //Catch if user tries to add entity without creating or loading a World first
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Load or create a world first!");
        }
    }

    /**
     * Function that creates and displays a new World based on the row and column size indicated by the user
     * Additionally updates left status area to provide useful information about the newly created World and updates the
     * right status area to indicate that a World has been created
     * @param event When "Create World" button is clicked
     */
    @FXML
    void createWorld(ActionEvent event) {
        try{
            //Assign user input to variables and create new World using these values
            int row = Integer.parseInt(rowField.getText());
            int column = Integer.parseInt(columnField.getText());
            System.out.println(row + " rows || " + column + " columns");
            World world = new World(row, column);
            setWorld(world);
            //Print and display new World and assign messages to left and right status for user's ease of access
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
            leftStatus.setStyle(null);
            leftStatus.setText("New " + row + "x" + column + " world created!");
            rightStatus.setText("World created!");
        } catch (NumberFormatException e) {
            //Catch invalid input and display error message to user
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }

    /**
     * Function that loads and displays an existing World from a .txt file selected by the user with defined dimensions
     * and existing entities that populate the World
     * @param event When "Load" menu option is selected from "File" menu
     */
    @FXML
    void loadFile(ActionEvent event) {
        //Initialize FileChooser so user can choose a file to open using open dialog window
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File fileOpen = fileChooser.showOpenDialog(new Stage());
        System.out.println(fileOpen);
        //Parse given file using Reader function from A2 and create the World using the file
        if(fileOpen != null){
            World world = Reader.loadWorld(fileOpen);
            setWorld(world);

            //Print and display loaded World and assign messages to left and right status for user's ease of access
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);
            rightStatus.setText("World loaded!");
        }
    }

    /**
     * Function that simply exits the program
     * @param event When "Quit" menu option is selected from "File" menu
     */
    @FXML
    void quitProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Function that sets left status area to "Currently adding heroes"
     * @param event When the "Hero" tab is selected
     */
    @FXML
    public void heroSelect(Event event) {
        //If left status isn't null, then assign style and text, prevents NullPointerException from occurring
        if(leftStatus != null){
            leftStatus.setStyle(null);
            leftStatus.setText("Currently adding heroes");
        }
    }

    /**
     * Function that sets left status area to "Currently adding monsters"
     * @param event When the "Monster" tab is selected
     */
    @FXML
    public void monsterSelect(Event event) {
        leftStatus.setStyle(null);
        leftStatus.setText("Currently adding monsters");
    }

    /**
     * Function that sets left status area to "Currently removing entities"
     * @param event When the "Remove" tab is selected
     */
    @FXML
    public void removeSelect(Event event) {
        leftStatus.setStyle(null);
        leftStatus.setText("Currently removing entities");
    }

    /**
     * Function that removes entities based on the user provided location information
     * @param actionEvent When "Remove Entity" button is clicked
     */
    @FXML
    public void removeEntity(ActionEvent actionEvent) {
        try{
            //Assign user input to variables
            int row = Integer.parseInt(removeRow.getText());
            int column = Integer.parseInt(removeColumn.getText());
            //Get entity at given row and column index
            Entity entity = getWorld().getEntity(row, column);
            String type = "Floor";

            //Decides on type of entity to print accurate message, otherwise default to Floor
            if(entity instanceof Hero){
                type = "Hero";
            }
            if(entity instanceof Monster){
                type = "Monster";
            }

            //Remove entity by assigning it as Null and print messages
            getWorld().addEntity(row, column, null);
            String removeStatus = "Removed " + type + " at (" + row + "," + column + ")!";
            System.out.println("\n" + removeStatus);
            leftStatus.setStyle(null);
            leftStatus.setText(removeStatus);

            //Update World View
            String worldString = getWorld().worldString();
            System.out.println(worldString);
            worldView.setStyle("-fx-font-family: Inconsolata; -fx-font-size: 40;");
            worldView.setText(worldString);

        } catch (NumberFormatException e) {
            //Catch invalid input and display error message to user
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }

    /**
     * Function that saves the current displayed World to a .txt file in the correct format, so it can be read again
     * @param actionEvent When "Save" menu option is selected from "File" menu
     * @throws FileNotFoundException When a filepath is invalid or a file can't be found
     */
    @FXML
    public void saveFile(ActionEvent actionEvent) throws FileNotFoundException {
        //Initialize StringBuilder to create the String for the File we want to write to
        StringBuilder fileBuild = new StringBuilder();
        //Append number of rows and columns
        fileBuild.append(getWorld().getRows()).append("\n").append(getWorld().getColumns());

        //Loop throw rows and columns in the World
        for(int row = 0; row <= getWorld().getRows() - 1; row++){
            for(int column = 0; column <= getWorld().getColumns() - 1; column++){
                //If the entity at the row column index is a Hero, append Hero attributes to the String
                if(getWorld().getEntity(row, column) instanceof Hero){
                    Entity hero = getWorld().getEntity(row, column);
                    fileBuild.append("\n").append(row).append(",").append(column).append(",").append("HERO").append(",")
                            .append(hero.getSymbol()).append(",").append(hero.getHealth()).append(",")
                            .append(hero.weaponStrength()).append(",").append(hero.armorStrength());
                }
                //If the entity at the row column index is a Monster, append Monster attributes to the String
                else if(getWorld().getEntity(row, column) instanceof Monster){
                    Entity monster = getWorld().getEntity(row, column);
                    char weapon = 0;
                    //Assign weapon character based on the Monster's weapon strength
                    if(monster.weaponStrength() == 2){
                        weapon = 'C';
                    }
                    else if(monster.weaponStrength() == 3){
                        weapon = 'A';
                    }
                    else if(monster.weaponStrength() == 4){
                        weapon = 'S';
                    }
                    fileBuild.append("\n").append(row).append(",").append(column).append(",").append("MONSTER")
                            .append(",").append(monster.getSymbol()).append(",").append(monster.getHealth())
                            .append(",").append(weapon);
                }
                //If an entity is anything else, it's a floor, so just append the row and column index
                else{
                    fileBuild.append("\n").append(row).append(",").append(column);
                }
            }
        }
        //Convert the StringBuilder to a String and print it to Terminal
        String fileString = fileBuild.toString();
        System.out.println("\n" + fileString);

        //Initialize FileChooser so user can choose a file to overwrite or create a new file using save dialog window
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("world.txt");
        File fileSave = fileChooser.showSaveDialog(new Stage());

        if(fileSave != null){
            //Initialize PrintWriter using given filepath, print our string to the file and close the PrintWriter
            PrintWriter pw = new PrintWriter(fileSave);
            pw.print(fileString);
            pw.close();
        }
        System.out.println(fileSave);
    }

    /**
     * Function to view Stats of any particular Entity in the World based on the location information provided by user
     * @param actionEvent When "View Entity" button is clicked
     */
    public void viewEntity(ActionEvent actionEvent) {
        try{
            //Assign user input to variables
            int row = Integer.parseInt(viewRow.getText());
            int column = Integer.parseInt(viewColumn.getText());
            //Get entity at given row and column index
            Entity entity = getWorld().getEntity(row, column);

            //If the entity at the row column index is a Hero, display it's appropriate attributes on the Stats View
            if(entity instanceof Hero){
                char symbol = entity.getSymbol();
                int health = entity.getHealth();
                statList.getItems().clear();
                statList.getItems().add("Type: Hero");
                statList.getItems().add("Symbol: " + symbol);
                statList.getItems().add("Health: " + health);
                statList.getItems().add("Weapon Strength: " + entity.weaponStrength());
                statList.getItems().add("Armor Strength: " + entity.armorStrength());
            }

            //If the entity at the row column index is a Monster, display it's appropriate attributes on the Stats View
            else if(entity instanceof Monster){
                char symbol = entity.getSymbol();
                int health = entity.getHealth();
                char weapon = 0;
                WeaponType weapontype = null;
                //Assign weapon character based on the Monster's weapon strength
                if(entity.weaponStrength() == 2){
                    weapon = 'C';
                    weapontype = WeaponType.getWeaponType(weapon);
                }
                else if(entity.weaponStrength() == 3){
                    weapon = 'A';
                    weapontype = WeaponType.getWeaponType(weapon);
                }
                else if(entity.weaponStrength() == 4){
                    weapon = 'S';
                    weapontype = WeaponType.getWeaponType(weapon);
                }
                statList.getItems().clear();
                statList.getItems().add("Type: Monster");
                statList.getItems().add("Symbol: " + symbol);
                statList.getItems().add("Health: " + health);
                statList.getItems().add("Weapon: " + weapontype);
                statList.getItems().add("Weapon Strength: " + entity.weaponStrength());
            }
            //Otherwise, the entity is a floor, so display basic floor information
            else{
                char symbol = '.';
                statList.getItems().clear();
                statList.getItems().add("Type: Floor");
                statList.getItems().add("Symbol: " + symbol);
            }
        } catch (NumberFormatException e) {
            //Catch invalid input and display error message to user
            leftStatus.setStyle("-fx-text-fill: red;");
            leftStatus.setText("Input must be numeric!");
        }
    }
}
