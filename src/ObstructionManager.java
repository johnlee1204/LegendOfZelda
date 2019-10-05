import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
public class ObstructionManager implements Serializable {
    private ArrayList<Obstruction> obstructions;
    transient private ArrayList<Image> images;
    private transient ArrayList<Image> cursorImages;
    private static final long serialVersionUID = -5230566917032422516L;
    private int[] rotations;
    private int obstructionId;
    private String[] imageFileNames;
    public ObstructionManager(){
        obstructions = new ArrayList<>();
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        try{
            File obstructionFolder  = new File("images/obstructions/");
            imageFileNames = obstructionFolder.list();
            Arrays.sort(imageFileNames);
            rotations = new int[imageFileNames.length];
            for(String fileName : imageFileNames) {
                BufferedImage temp = ImageIO.read(new File("images/obstructions/" + fileName));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE, Constants.TILE_SIZE, Image.SCALE_SMOOTH));
                cursorImages.add(temp.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateImagesAfterLoad(){
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        File obstructionFolder  = new File("images/obstructions/");
        imageFileNames = obstructionFolder.list();
        try{
            for(String fileName : imageFileNames) {
                BufferedImage temp = ImageIO.read(new File("images/obstructions/" + fileName));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE, Constants.TILE_SIZE, Image.SCALE_SMOOTH));
                System.out.println(fileName);
                cursorImages.add(temp.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int[] imageNums = getObstructionImageNums();
        for(int i = 0; i<obstructions.size();i++){
            obstructions.get(i).setObstructionImage(imageNums[i],images.get(imageNums[i]));
            obstructions.get(i).updateRotation();
        }
    }

    public void draw(Graphics g,boolean showHidden){
        for(Obstruction ob : obstructions){
            ob.draw(g,showHidden);
        }
    }

    public void drawOnCursor(Graphics g, int imageIndex,int x, int y){
        g.drawImage(cursorImages.get(imageIndex),x,y,null);
    }

    public void update(){
        for(Obstruction ob : obstructions){
            ob.update();
        }
    }

    public void addObstruction(int obstruction,int x, int y){
        for(Obstruction ob : obstructions){
            for(Point p : ob.getObstructionPoints())
            if(p.x==x/Constants.TILE_SIZE*Constants.TILE_SIZE&&p.y==y/Constants.TILE_SIZE*Constants.TILE_SIZE){
                return;
            }
        }
        Point[] obstructionPointsForObstruction = new Point[]{new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE)};
        if(obstruction==0){
            obstructions.add(new Angry(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==1){
            Point[] tablePoints = new Point[]{
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*2)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*2)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*3)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*3)/Constants.TILE_SIZE*Constants.TILE_SIZE)
                    )};
            for(Obstruction ob : obstructions){
                for(Point p : ob.getObstructionPoints())
                    for(Point tableP : tablePoints) {
                        if (tableP.x == p.x / Constants.TILE_SIZE * Constants.TILE_SIZE && tableP.y == p.y / Constants.TILE_SIZE * Constants.TILE_SIZE) {
                            return;
                        }
                    }
            }
            obstructions.add(new BasicTable(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, tablePoints));
        }
        else if(obstruction==2){
            Point[] tablePoints = new Point[]{
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*2)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*2)/Constants.TILE_SIZE*Constants.TILE_SIZE))//,
//                    (new Point(x/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*3)/Constants.TILE_SIZE*Constants.TILE_SIZE)),
//                    (new Point((x+Constants.TILE_SIZE)/Constants.TILE_SIZE*Constants.TILE_SIZE,(y+Constants.TILE_SIZE*3)/Constants.TILE_SIZE*Constants.TILE_SIZE))
            };
            for(Obstruction ob : obstructions){
                for(Point p : ob.getObstructionPoints())
                    for(Point tableP : tablePoints) {
                        if (tableP.x == p.x / Constants.TILE_SIZE * Constants.TILE_SIZE && tableP.y == p.y / Constants.TILE_SIZE * Constants.TILE_SIZE) {
                            return;
                        }
                    }
            }
            obstructions.add(new Bed(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, tablePoints));
        }
        else if(obstruction==3){
            obstructions.add(new Bush(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==4){
            obstructions.add(new GreenRock(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==5){
            obstructions.add(new HeartContainer(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==6){
            obstructions.add(new HiddenObstruction(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==7) {
            obstructions.add(new TreeStump(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==8){
            obstructions.add(new Sign(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }
        else if(obstruction==9){
            obstructions.add(new TreeStump(obstructionId,obstruction,images.get(obstruction),rotations[obstruction],x,y, obstructionPointsForObstruction));
        }

        obstructionId++;

    }

    public void removeObstruction(int x, int y){
        Obstruction obstruction = findObstructionByLocation(x/Constants.TILE_SIZE*Constants.TILE_SIZE,y/Constants.TILE_SIZE*Constants.TILE_SIZE);;
        if(obstruction==null || obstruction.getObstructionImageNum() == 5){
            return;
        }
        int id = obstruction.getObstructionId();
        obstructions.remove(obstructions.get(id));
        //check if table if so remove all points
        for (Obstruction ob: obstructions) {
            if(ob.getObstructionId()>id)
                ob.setObstructionId(ob.getObstructionId()-1);
        }
        obstructionId--;
    }

    public void rotate(int imageIndex) {
        // The required drawing location
        double rotationRequired = Math.toRadians(90);
        double locationX = 32 / 2.0;
        double locationY = 32 / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        cursorImages.set(imageIndex, op.filter(convertToBufferedImage( cursorImages.get(imageIndex)),null));
        rotationRequired = Math.toRadians(90);
        locationX = Constants.TILE_SIZE/2.0;
        locationY = Constants.TILE_SIZE/2.0;
        tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        images.set(imageIndex, op.filter(convertToBufferedImage( images.get(imageIndex)),null));
        rotations[imageIndex]++;
    }

    public static BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public int[] getObstructionImageNums(){
        int[] imageNums = new int[obstructions.size()];
        for(int i = 0; i < imageNums.length;i++){
            imageNums[i] = obstructions.get(i).getObstructionImageNum();
        }
        return imageNums;
    }

    public void setObstructions(ArrayList<Obstruction> obstructions){
        this.obstructions = obstructions;
    }

    public ArrayList<Obstruction> getObstructions(){
        return obstructions;
    }

    public void setObstructionId(int obstructionId){
        this.obstructionId = obstructionId;
    }

    public int getObstructionId(){
        return obstructionId;
    }

    private Obstruction findObstructionByLocation(int x, int y){
        for(Obstruction obstruction : obstructions){
            if(obstruction.getX()==x && obstruction.getY()==y){
                return obstruction;
            }
        }
        return null;
    }

    public int getImageCount(){
        return images.size();
    }
}
