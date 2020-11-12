import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements KeyListener {

    private final JPanel gridPanel = new JPanel();
    private final JPanel playerPanel = new JPanel();

    private final Game game;

    public GUI(Game game) {
        super(game.getTitle());
        this.game = game;
        setSize(1000, 950);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        add(BorderLayout.NORTH, getPlayerPanel(game.getPlayer()));
        add(BorderLayout.SOUTH, getGridPanel(game.getGrid()));
        setVisible(true);
    }

    public JPanel getPlayerPanel(Player player) {
        resetPanel(playerPanel);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
        JLabel name_label = new JLabel("Name: " + player.getName());
        JLabel health_label = new JLabel("Health: " + player.getHealth());
        JLabel level_label = new JLabel("Level: " + player.getLevel());
        JLabel experience_label = new JLabel("Experience: " + player.getExperience());
        JLabel inventory_label = new JLabel("Inventory: " + player.getInventoryString());
        JLabel equipped_label = new JLabel("Equpped: " + player.getWeapon().getName() + " (x" + player.getWeapon().getMultiplier() + ")");
        playerPanel.add(name_label);
        playerPanel.add(health_label);
        playerPanel.add(level_label);
        playerPanel.add(experience_label);
        playerPanel.add(inventory_label);
        playerPanel.add(equipped_label);
        return playerPanel;
    }

    public JPanel getGridPanel(Grid grid) {
        resetPanel(gridPanel);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gridPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        for (int x = 0; x < game.getSize(); x++) {
            for (int y = 0; y < game.getSize(); y++) {
                Cell cell = new Cell(grid.get(x, y));
                constraints.gridx = y;
                constraints.gridy = x;
                gridPanel.add(cell, constraints);
            }
        }
        return gridPanel;
    }

    public void resetPanel(JPanel panel) {
        panel.setVisible(false);
        panel.removeAll();
        panel.validate();
        panel.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        try {
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case 38:
                    game.move("↑");
                    break;
                case 40:
                    game.move("↓");
                    break;
                case 37:
                    game.move("←");
                    break;
                case 39:
                    game.move("→");
                    break;
            }
            getGridPanel(game.getGrid());
            getPlayerPanel(game.getPlayer());
        } catch (ArrayIndexOutOfBoundsException e) {
            //Do nothing
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Do nothing
    }

    public class Cell extends JPanel {

        private BufferedImage sprite;

        public Cell(GridItem gridItem) {
            Border hover = BorderFactory.createLineBorder(Color.WHITE, 5);
            Border empty = BorderFactory.createEmptyBorder(0, 0, 0, 0);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    JPanel parent = (JPanel) e.getSource();
                    parent.setBorder(hover);
                    parent.revalidate();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int type = gridItem.getType();
                    String information = "You can walk here.";
                    switch (type) {
                        case Utils.PLAYER:
                            information = "This is you.";
                            break;
                        case Utils.ENEMY:
                            Enemy enemy = (Enemy) gridItem;
                            information = "Level: " + enemy.getLevel() + ",\nHealth: " + enemy.getHealth() + ".";
                            break;
                        case Utils.FOOD:
                            Food food = (Food) gridItem;
                            information = "Restores: " + food.getRestoreAmount() + " Health.";
                            break;
                        case Utils.WEAPON:
                            Weapon weapon = (Weapon) gridItem;
                            information = "Attack Multiplier: x" + weapon.getMultiplier() + ".";
                            break;
                        case Utils.ITEM:
                            information = "Uses: None.";
                            break;
                    }
                    Utils.sendMessage(information, gridItem.getName());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JPanel parent = (JPanel) e.getSource();
                    parent.setBorder(empty);
                    parent.revalidate();
                }
            });
            try {
                sprite = ImageIO.read(new File("sprites/" + gridItem.getSprite()));
            } catch (IOException e) {
                Utils.sendMessage("Failed to apply grid item sprite", "Error");
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(80, 80);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(sprite, 0, 0, this);
        }
    }

}
