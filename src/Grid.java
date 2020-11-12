import java.util.Random;

public class Grid {

    private final int size;
    private final GridItem[][] grid;

    public Grid(int size) {
        this.size = size;
        this.grid = new GridItem[size][size];
        initialise();
    }

    public GridItem[][] getGrid() {
        return grid;
    }

    public void initialise() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                grid[x][y] = new Ground();
            }
        }
    }

    public void place(GridItem gridItem, int x, int y) {
        grid[x][y] = gridItem;
    }

    public void add(GridItem gridItem) {
        boolean added = false;
        while (!added) {
            int x = getRandomTile();
            int y = getRandomTile();
            if (get(x, y).getType() != Utils.GROUND) {
                return;
            }
            place(gridItem, x, y);
            added = true;
        }
    }

    public void remove(int x, int y) {
        place(new Ground(), x, y);
    }

    public int[] find(GridItem gridItem) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] == gridItem) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public GridItem get(int x, int y) {
        return grid[x][y];
    }

    public int getRandomTile() {
        Random random = new Random();
        return random.nextInt(size);
    }
}
