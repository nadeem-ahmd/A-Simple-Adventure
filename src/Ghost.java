public class Ghost extends Enemy implements GridItem {

    public Ghost() {
        super("Ghost", 5, 50);
    }

    @Override
    public String getSprite() {
        return "ghost.png";
    }
}
