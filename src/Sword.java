public class Sword extends Item implements Weapon, GridItem {

    private final double multiplier;

    public Sword() {
        super("Sword");
        this.multiplier = 1.50;
    }

    @Override
    public int getType() {
        return Utils.WEAPON;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String getAttackMessage() {
        return "You strike with your " + getName().toLowerCase();
    }

    @Override
    public String getSprite() {
        return "sword.png";
    }
}
