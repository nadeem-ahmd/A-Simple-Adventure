import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Game {

    private final String title;
    private final int size;

    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private GridItem[] items;

    public Game(String title, int size) {
        this.title = title;
        this.size = size;
        this.grid = new Grid(size);
        if (Utils.fileExists(Utils.PLAYER_SAVE) && userConfirms()) {
            loadSave();
        } else {
            initialise();
        }
    }

    public void initialise() {
        player = Utils.getDefaultPlayer();
        grid.place(player, Utils.STARTING_X, Utils.STARTING_Y);
        enemies = Utils.getDefaultEnemies();
        for (Enemy enemy : enemies) {
            grid.add(enemy);
        }
        items = Utils.getDefaultDrops();
        for (GridItem item : items) {
            grid.add(item);
        }
    }

    public boolean userConfirms() {
        String text = "Start a new save or load existing save?";
        int dialog = JOptionPane.showOptionDialog(null, text, "Save Found", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"New Game", "Existing Game"}, "Existing Game");
        return (dialog == JOptionPane.NO_OPTION);
    }

    public void writePlayerInformation() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(Utils.PLAYER_SAVE);
            writer.write(player.getInformation());
            writer.close();
        } catch (FileNotFoundException e) {
            Utils.sendMessage("Failed to write player data", "Error");
        }
    }

    public void writeGridInformation() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(Utils.GRID_SAVE);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    String line = "";
                    if (grid.get(x, y).getType() != Utils.GROUND) {
                        switch (grid.get(x, y).getType()) {
                            case Utils.PLAYER:
                                line = "P," + x + "," + y;
                                break;
                            case Utils.ENEMY:
                                Enemy enemy = (Enemy) grid.get(x, y);
                                line = "E," + enemy.getName() + "," + x + "," + y;
                                break;
                            case Utils.FOOD:
                                Food food = (Food) grid.get(x, y);
                                line = "F," + food.getName() + "," + x + "," + y;
                                break;
                            case Utils.ITEM:
                                Item item = (Item) grid.get(x, y);
                                line = "D," + item.getName() + "," + x + "," + y;
                                break;
                            case Utils.WEAPON:
                                Weapon weapon = (Weapon) grid.get(x, y);
                                line = "W," + weapon.getName() + "," + x + "," + y;
                                break;
                        }
                        writer.write(line + "\n");
                    }
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            Utils.sendMessage("Failed to write grid data", "Error");
        }
    }

    public void saveProgress() {
        writePlayerInformation();
        writeGridInformation();
    }

    public void readPlayerInformation() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(Utils.PLAYER_SAVE));
            String name = reader.readLine();
            int level = Integer.parseInt(reader.readLine());
            double health = Double.parseDouble(reader.readLine());
            double experience = Double.parseDouble(reader.readLine());
            ArrayList<Item> inventory = Utils.getInventoryFromSave(reader.readLine());
            Weapon weapon = Utils.getWeaponFromSave(reader.readLine());
            player = new Player(name, level, health, experience, inventory, weapon);
            reader.close();
        } catch (FileNotFoundException e) {
            Utils.sendMessage("Failed to find player data file", "Error");
        } catch (IOException e) {
            Utils.sendMessage("Failed to read player data file", "Error");
        }
    }

    public void readGridInformation() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(Utils.GRID_SAVE));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(",");
                switch (values[0]) {
                    case "P":
                        grid.place(player, Utils.parseInt(values[1]), Utils.parseInt(values[2]));
                        break;
                    case "E":
                        Enemy enemy = null;
                        if (values[1].equals("Zombie")) {
                            enemy = new Zombie();
                        } else if (values[1].equals("Ghost")) {
                            enemy = new Ghost();
                        }
                        grid.place(enemy, Utils.parseInt(values[2]), Utils.parseInt(values[3]));
                        break;
                    case "F":
                        Food food = null;
                        if (values[1].equals("Apple")) {
                            food = new Apple();
                        } else if (values[1].equals("Medicine")) {
                            food = new Medicine();
                        }
                        grid.place(food, Utils.parseInt(values[2]), Utils.parseInt(values[3]));
                        break;
                    case "W":
                        GridItem weapon = null;
                        if (!values[1].contains("Hands")) {
                            if (values[1].contains("Sword")) {
                                weapon = new Sword();
                            } else if (values[1].contains("Staff")) {
                                weapon = new Staff();
                            }
                            grid.place(weapon, Utils.parseInt(values[2]), Utils.parseInt(values[3]));
                        }
                        break;
                    case "D":
                        GridItem item = null;
                        if (values[1].equals("Book")) {
                            item = new Book();
                        } else if (values[1].equals("Chalice")) {
                            item = new Chalice();
                        }
                        grid.place(item, Utils.parseInt(values[2]), Utils.parseInt(values[3]));
                        break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            Utils.sendMessage("Failed to find grid data file", "Error");
        } catch (IOException e) {
            Utils.sendMessage("Failed to read grid data file", "Error");
        }
    }

    public void loadSave() {
        readPlayerInformation();
        readGridInformation();
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public void move(String direction) {
        int[] initialPosition = grid.find(player);
        int[] targetPosition = grid.find(player);
        switch (direction) {
            case "↑":
                targetPosition[0]--;
                break;
            case "↓":
                targetPosition[0]++;
                break;
            case "←":
                targetPosition[1]--;
                break;
            case "→":
                targetPosition[1]++;
                break;
        }
        GridItem target = grid.get(targetPosition[0], targetPosition[1]);
        if (Utils.isDrop(target.getType())) {
            Item item = (Item) target;
            switch (target.getType()) {
                case Utils.FOOD:
                    if (player.getHealth() == 100) {
                        String text = "You already have full health, have the " + item.getName() + " anyway?";
                        if (JOptionPane.showOptionDialog(null, text, "Health", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "No")
                                == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                    player.eat((Food) item);
                    break;
                case Utils.WEAPON:
                    Weapon weapon = (Weapon) item;
                    if (!player.getWeapon().getName().equals("Hands")) {
                        if (player.getWeapon().getName().equalsIgnoreCase(weapon.getName())) {
                            Utils.sendMessage("You already have a " + item.getName() + " equipped.", "Equip");
                            return;
                        }
                        String text = "You have a " + player.getWeapon().getName() + ", replace it with the " + item.getName() + "?";
                        if (JOptionPane.showOptionDialog(null, text, "Equip", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "No")
                                == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                    player.equip(weapon);
                    break;
                case Utils.ITEM:
                    player.take(item);
                    break;
            }
        }
        if (Utils.isEnemy(target)) {
            Fight fight = new Fight(player, (Enemy) target);
            if (fight.isReady()) {
                fight.initialise();
                if (fight.isVictorious()) {
                    grid.remove(grid.find(target)[0], grid.find(target)[1]);
                } else {
                    player.die();
                    System.exit(0);
                }
            } else {
                fight.flee();

                grid.place(new Ground(), initialPosition[0], initialPosition[1]);
                grid.add(player);
            }
        }
        if (target.isWalkable()) {
            grid.place(new Ground(), initialPosition[0], initialPosition[1]);
            grid.place(player, targetPosition[0], targetPosition[1]);
        }
        saveProgress();
    }
}
