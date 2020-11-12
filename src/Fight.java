import javax.swing.*;

public class Fight {

    private final Player player;
    private final Enemy enemy;
    private Boolean victorious;

    public Fight(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isVictorious() {
        return victorious;
    }

    public void setVictorious(Boolean victorious) {
        this.victorious = victorious;
    }

    public boolean isReady() {
        String text = "Fight or Flee against the " + enemy.getName() + " (" + enemy.getHealth() + " health)";
        return JOptionPane.showOptionDialog(null, text, "Combat", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Fight", "Flee"}, "Flee")
                == JOptionPane.YES_OPTION;
    }

    public void initialise() {
        Utils.sendMessage(player.getWeapon().getAttackMessage() + " against the " + enemy.getName() + "...", "Combat");
        enemy.setHealth(0);
        player.setHealth(player.getHealth() - 10);
        setVictorious(true);
        Utils.sendMessage("You kill the " + enemy.getName() + "!\n[-10 health, +25 experience]", "Combat");
        player.giveExperience(25);
    }

    public void flee() {
        player.setHealth(player.getHealth() - 5);
        Utils.sendMessage("As you attempt to flee, the " + enemy.getName() + " strikes you for 5 health!", "Combat");
    }
}
