import java.util.ArrayList;

public class Player implements GridItem {

    private String name;
    private int level;
    private double health;
    private double experience;
    private ArrayList<Item> inventory;
    private Weapon weapon;

    public Player(String name, int level, double health, double experience) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.experience = experience;
        this.inventory = new ArrayList<>();
        this.weapon = new Hands();
    }

    public Player(String name, int level, double health, double experience, ArrayList<Item> inventory, Weapon weapon) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.experience = experience;
        this.inventory = inventory;
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getInventoryString() {
        if (getInventory().isEmpty()) {
            return "Empty";
        }
        String formatted = "";
        for (Item i : inventory) {
            if (!formatted.isEmpty()) {
                formatted += ", ";
            }
            formatted += i.getName();
        }
        return formatted;
    }

    public String getWeaponString() {
        return weapon.getName() + ", x" + weapon.getMultiplier();
    }

    public void eat(Food food) {
        if (health + food.getRestoreAmount() <= 100) {
            setHealth(getHealth() + food.getRestoreAmount());
        } else {
            setHealth(100);
        }
    }

    public void take(Item item) {
        inventory.add(item);
    }

    public void equip(Weapon weapon) {
        setWeapon(weapon);
    }

    public void giveExperience(int xp) {
        setExperience(getExperience() + xp);
        if (getExperience() >= 100) {
            setLevel(getLevel() + 1);
            setExperience(0);
            Utils.sendMessage("You have leveled up! You are now level " + level + ".", "Level");
        }
    }

    public void die() {
        Utils.sendMessage("You died.", "Game Over");
    }

    public String getInformation() {
        return name + "\n" + level + "\n" + health + "\n" + experience + "\n" + "[" + getInventoryString() + "]\n" + "[" + getWeaponString() + "]";
    }

    @Override
    public int getType() {
        return Utils.PLAYER;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "player.png";
    }
}
