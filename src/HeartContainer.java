import java.awt.*;
import java.io.Serializable;

public class HeartContainer extends Obstruction implements Serializable {
    private static final long serialVersionUID = 5434932000510836584L;
    public HeartContainer(int obstructionId, int imageNum, Image image, int rotation, int x, int y, Point[] obstructionPoints){
        super(obstructionId,imageNum,image,rotation,x,y, obstructionPoints);
    }
}
