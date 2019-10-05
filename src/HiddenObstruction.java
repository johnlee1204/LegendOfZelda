import java.awt.*;
import java.io.Serializable;

public class HiddenObstruction extends Obstruction implements Serializable {
    private static final long serialVersionUID = 5434922005510736584L;

    public HiddenObstruction(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image,rotation,x,y, obstructionPoints);
//        this.image = image;
//        this.x = x / Constants.TILE_SIZE * Constants.TILE_SIZE;
//        this.y = y / Constants.TILE_SIZE * Constants.TILE_SIZE;
    }
    @Override
    public void draw(Graphics g, boolean showHidden){
        if(showHidden){
            g.drawImage(this.image,x,y,null);
        }
    }
}
