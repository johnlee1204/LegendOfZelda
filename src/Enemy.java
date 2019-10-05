import java.awt.*;

public abstract class Enemy {
    private int x;
    private int y;
    private String name;
    private Image image;
    private int imageNum;
    private int health;
    private Rectangle hitBox;

    public Enemy(int x, int y, String name, Image image, int imageNum, int health) {

        this.x = x;
        this.y = y;
        this.name = name;
        this.image = image;
        this.imageNum = imageNum;
        this.health = health;
        hitBox = new Rectangle(x,y,image.getWidth(null), image.getHeight(null));
    }

    public void draw(Graphics g) {
        g.drawImage(image, this.x, this.y, null);
    }

    public void update() {

    }

    public void damage(int damage) {
        this.health -= damage;
    }
    public Image getImage() {
        return this.image;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

}
