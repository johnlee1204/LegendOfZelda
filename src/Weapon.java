import java.awt.*;

public abstract class Weapon extends Item{
    protected int damage;
    public Weapon(Image image, int imageNum, int value, int x, int y, int damage) {
        super(image, imageNum, value, x, y);
        this.damage = damage;
    }
}
