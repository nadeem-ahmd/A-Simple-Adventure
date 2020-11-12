public class Ground implements GridItem {

    @Override
    public String getName() {
        return "Ground";
    }

    @Override
    public int getType() {
        return Utils.GROUND;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public String getSprite() {
        return "ground.png";
    }

}
