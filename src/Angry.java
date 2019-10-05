import java.awt.*;
import java.io.Serializable;

public class Angry extends Obstruction implements Serializable {
    public Angry(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image,rotation,x,y, obstructionPoints);
    }
}
