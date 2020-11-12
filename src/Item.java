public class Item implements GridItem {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    @Override
    public int getType() {
        return Utils.ITEM;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public String getSprite() {
        throw new UnsupportedOperationException("Unsupported item.");
    }
}
