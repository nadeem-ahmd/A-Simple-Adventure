public class Hands extends Item implements Weapon {

    private final double multiplier;

    public Hands() {
        super("Hands");
        this.multiplier = 1.00;
    }

    @Override
    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String getAttackMessage() {
        return "You punch";
    }

}
