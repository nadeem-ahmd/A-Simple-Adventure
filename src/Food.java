public abstract class Food extends Item implements GridItem {

    private final double restore;

    public Food(String name, int restore) {
        super(name);
        this.restore = restore;
    }

    public double getRestoreAmount() {
        return restore;
    }

    @Override
    public int getType() {
        return Utils.FOOD;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public String getSprite() {
        return null;
    }
}
