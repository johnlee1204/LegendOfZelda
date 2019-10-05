import java.awt.*;
import java.io.Serializable;

public class Pot extends Obstruction implements Serializable {
    public Pot(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image,rotation,x,y, obstructionPoints);
    }
}
