public class Book extends Item {

    public Book() {
        super("Book");
    }

    @Override
    public String getSprite() {
        return "book.png";
    }
}
