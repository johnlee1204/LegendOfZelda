import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.Serializable;

public abstract class Obstruction implements Serializable {
    transient protected Image image;
    protected int x, y, width, height, imageNum, obstructionId, rotation;
    private Point[] obstructionPoints;
    private static final long serialVersionUID = -1821430141095366854L;
    protected Rectangle rect;
    public Obstruction(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints) {
        this.obstructionId = obstructionId;
        this.image = image;
        this.imageNum = imageNum;
        this.x = x / Constants.TILE_SIZE * Constants.TILE_SIZE;
        this.y = y / Constants.TILE_SIZE * Constants.TILE_SIZE;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.rotation = rotation;
        this.obstructionPoints = obstructionPoints;
        rect = new Rectangle(this.x,this.y,width,height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRect(){
        return rect;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public int getObstructionImageNum() {
        return imageNum;
    }

    public int getObstructionId() {
        return obstructionId;
    }

    public void setObstructionId(int obstructionId) {
        this.obstructionId = obstructionId;
    }

    public void setObstructionImage(int imageNum, Image image) {
        this.image = image;
        this.imageNum = imageNum;
    }

    public void draw(Graphics g,boolean showHidden) {
        g.drawImage(image, x, y, null);
        //g.setColor(Color.BLUE);
        //1g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
    }

    public void update() {

    }

    public void updateRotation(){
        for(int i = 0; i < rotation; i++){
            this.rotate();
        }
    }

    public void rotate() {
        // The required drawing location
        int drawLocationX = x;
        int drawLocationY = y;
        double rotationRequired = Math.toRadians(90);
        double locationX = width / 2.0;
        double locationY = height / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(ObstructionManager.convertToBufferedImage(image),null);
        x = drawLocationX;
        y = drawLocationY;
    }

    public Point[] getObstructionPoints() {
        return obstructionPoints;
    }

    public void setObstructionPoints(Point[] obstructionPoints) {
        this.obstructionPoints = obstructionPoints;
    }
}
