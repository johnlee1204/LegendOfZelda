import java.awt.*;
import java.io.Serializable;

public class Bed extends Obstruction implements Serializable {
    private static final long serialVersionUID = 5434932005510736584L;
    public Bed(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image.getScaledInstance(Constants.TILE_SIZE*2,Constants.TILE_SIZE*3,Image.SCALE_SMOOTH),rotation,x,y, obstructionPoints);
    }
    public void setObstructionImage(int imageNum, Image image) {
        this.image = image.getScaledInstance( Constants.TILE_SIZE*2,Constants.TILE_SIZE*3,Image.SCALE_SMOOTH);
        this.imageNum = imageNum;
    }
}
