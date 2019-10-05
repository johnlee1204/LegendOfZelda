import java.awt.*;

public abstract class Item {

    protected Image image;
    protected int imageNum, value, x, y;

    public Item(Image image, int imageNum, int value, int x, int y) {
        this.image = image;
        this.imageNum = imageNum;
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
