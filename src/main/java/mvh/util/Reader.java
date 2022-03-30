package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;
import java.util.*;

/*
  Name: Faisal Islam
  Date: March 29, 2022
  Tutorial: T10- Tejash
 */

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {

    /**
     * Load the world from the given file
     * (Do not expect students to create anything near as robust as this file reading method!)
     * (A better design would also use sub-functions.)
     *
     * @param fileWorld The world file to load
     * @return A World created from the world file
     */
    public static World loadWorld(File fileWorld) {
        //create FileReader and BufferedReader objects to read lines
        FileReader fr = null;
        try {
            fr = new FileReader(fileWorld);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        //initialize rows and columns as zero
        //Read line 1, assign as rows
        //Read line 2, assign as columns
        int rows = 0;
        try {
            rows = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int columns = 0;
        try {
            columns = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rXc = rows * columns;
        //create new world with size rows, columns
        World world = new World(rows,columns);
        //loop for rows * columns number of following lines
        for(int i = 0; i < rXc; i++){
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert line != null;
            //split line by commas because CSV
            List<String> list = new ArrayList<>(Arrays.asList(line.split(",")));
            //if more than two elements in line
            if(list.size() > 2){
                //if third element in line is "MONSTER" attach correct stats and info
                if(Objects.equals(list.get(2), "MONSTER")){
                    int mons_row = Integer.parseInt(list.get(0));
                    int mons_column = Integer.parseInt(list.get(1));
                    String symbol_string = list.get(3);
                    char symbol = symbol_string.charAt(0);
                    int health = Integer.parseInt(list.get(4));
                    String weapon_string = list.get(5);
                    char weapon = weapon_string.charAt(0);
                    WeaponType weapontype = WeaponType.getWeaponType(weapon);

                    Monster Monster = new Monster(health, symbol, weapontype);
                    world.addEntity(mons_row, mons_column, Monster);
                }
                //if third element in line is "HERO" attach correct stats and info
                if(Objects.equals(list.get(2), "HERO")){
                    int hero_row = Integer.parseInt(list.get(0));
                    int hero_column = Integer.parseInt(list.get(1));
                    String symbol_string = list.get(3);
                    char symbol = symbol_string.charAt(0);
                    int health = Integer.parseInt(list.get(4));
                    int weaponStrength = Integer.parseInt(list.get(5));
                    int armorStrength = Integer.parseInt(list.get(6));

                    Hero Hero = new Hero(health, symbol, weaponStrength, armorStrength);
                    world.addEntity(hero_row, hero_column, Hero);
                }
            }
        }
        return world;
    }
}
