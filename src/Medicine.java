public class Medicine extends Food {

    public Medicine() {
        super("Medicine", 50);
    }

    @Override
    public String getSprite() {
        return "medicine.png";
    }
}
