public class Staff extends Item implements Weapon, GridItem {

    private final double multiplier;

    public Staff() {
        super("Staff");
        this.multiplier = 1.75;
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
        return "You cast a spell with your " + getName().toLowerCase();
    }

    @Override
    public String getSprite() {
        return "staff.png";
    }
}
