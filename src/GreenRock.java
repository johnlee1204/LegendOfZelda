import java.awt.*;
import java.io.Serializable;

public class GreenRock extends Obstruction implements Serializable {
    private static final long serialVersionUID = 5434922005510736584L;
    public GreenRock(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image,rotation,x,y, obstructionPoints);
    }
}
