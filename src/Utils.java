import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public final class Utils {

    public static final String GAME_NAME = "A Simple Adventure";

    public static final String PLAYER_SAVE = "data/player";
    public static final String GRID_SAVE = "data/grid";

    public static final int GRID_SIZE = 10;
    public static final int STARTING_X = 0;
    public static final int STARTING_Y = 0;

    public static final int GROUND = 0;
    public static final int PLAYER = 1;
    public static final int ENEMY = 2;
    public static final int FOOD = 3;
    public static final int WEAPON = 4;
    public static final int ITEM = 5;

    public static boolean isDrop(int type) {
        return type == FOOD || type == WEAPON || type == ITEM;
    }

    public static boolean isEnemy(GridItem gridItem) {
        return gridItem.getType() == ENEMY;
    }

    public static void sendMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static String getInput(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    public static int parseInt(String s) {
        return Integer.parseInt(s);
    }

    public static double parseDouble(String s) {
        return Double.parseDouble(s);
    }

    public static Player getDefaultPlayer() {
        String name = getInput("What would you like your character to be called?", "Name");
        return new Player(name, 1, 100, 0.0);
    }

    public static GridItem[] getDefaultDrops() {
        return new GridItem[]{new Apple(), new Apple(), new Medicine(), new Sword(), new Staff(), new Book(), new Chalice()};
    }

    public static Enemy[] getDefaultEnemies() {
        return new Enemy[]{new Zombie(), new Zombie(), new Zombie(), new Ghost(), new Ghost(), new Ghost()};
    }

    public static Weapon getWeaponFromSave(String save) {
        if (save.contains("Sword")) {
            return new Sword();
        } else if (save.contains("Staff")) {
            return new Staff();
        }
        return new Hands();
    }

    public static ArrayList<Item> getInventoryFromSave(String save) {
        ArrayList<Item> inventory = new ArrayList<>();
        if (!save.equals("[Empty]")) {
            String[] items = save.substring(1, save.length() - 1).split(",");
            for (String item : items) {
                inventory.add(new Item(item));
            }
        }
        return inventory;
    }
}
