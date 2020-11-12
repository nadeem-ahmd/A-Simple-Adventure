public class Zombie extends Enemy implements GridItem {

    public Zombie() {
        super("Zombie", 1, 25);
    }

    @Override
    public String getSprite() {
        return "zombie.png";
    }
}
