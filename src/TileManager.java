import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.*;
import java.util.Arrays;
public class TileManager implements Serializable{
    private ArrayList<Tile> tiles;
    private transient ArrayList<Image> images;
    private transient ArrayList<Image> cursorImages;
    private int tileId;
    private int[] rotations;
    private static final long serialVersionUID = 1404705954920351561L;
    private String[] imageSources;
    public TileManager() {
        tiles = new ArrayList<>();
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        File tileFolder  = new File("images/tiles/");
        imageSources = tileFolder.list();
        assert imageSources != null;
        Arrays.sort(imageSources);
        try{
            for(String src : imageSources){
                BufferedImage temp = ImageIO.read(new File("images/tiles/"+src));
                cursorImages.add(temp.getScaledInstance(32,32,Image.SCALE_SMOOTH));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE,Constants.TILE_SIZE,Image.SCALE_SMOOTH));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        for(int i = 0; i<Constants.SCREEN_WIDTH/Constants.TILE_SIZE;i++){
            for(int j = 0; j < Constants.SCREEN_HEIGHT/Constants.TILE_SIZE;j++){
                tiles.add(new Tile(0,images.get(0),j,i,tileId));
                tileId++;
            }
        }
        rotations = new int[imageSources.length];
    }

    public void updateImagesAfterLoad(){
        images = new ArrayList<Image>();
        cursorImages = new ArrayList<Image>();
        try{
            for(String src : imageSources){
                BufferedImage temp = ImageIO.read(new File("images/tiles/"+src));
                cursorImages.add(temp.getScaledInstance(32,32,Image.SCALE_SMOOTH));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE,Constants.TILE_SIZE,Image.SCALE_SMOOTH));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        int[] imageNums = getTileImageNums();
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setTileImage(imageNums[i], images.get(imageNums[i]));
        }
    }

    public void updateImageRotationsAfterLoad(){
        int[] tileRotations = getTileRotations();
        for(int i = 0; i<tileRotations.length;i++){
            tiles.get(i).setRotation(tileRotations[i]);
            tiles.get(i).updateRotation();
        }
    }

    public void drawOnCursor(Graphics g, int imageIndex,int x, int y){
        g.drawImage(cursorImages.get(imageIndex),x,y,null);
        //g.drawImage(cursorImages.get(imageIndex).getScaledInstance(128,128,Image.SCALE_SMOOTH),Constants.SCREEN_WIDTH-128,0,null);
    }
    
    public void draw(Graphics g){
        for(Tile tile : tiles){
            tile.draw(g);
        }
    }

    public void update(){
        for(Tile tile : tiles){
            tile.update();
        }
    }

    public void changeTile(int imageIndex, int row, int col){
        int tileId = findTileByLocation(row,col);
        if(tileId!=-1) {
            tiles.get(tileId).setTileImage(imageIndex, images.get(imageIndex));
            tiles.get(tileId).setRotation(rotations[imageIndex]);
        }
    }

    public int[] getTileRotations(){
        int[] output = new int[tiles.size()];
        for(int i = 0; i< output.length;i++){
            output[i] = tiles.get(i).getRotation();
        }
        return output;
    }

    public void rotate(int imageIndex) {
        // The required drawing location
        double rotationRequired = Math.toRadians(90);
        double locationX = 32 / 2.0;
        double locationY = 32 / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        cursorImages.set(imageIndex, op.filter(ObstructionManager.convertToBufferedImage( cursorImages.get(imageIndex)),null));
        rotationRequired = Math.toRadians(90);
        locationX = Constants.TILE_SIZE/2.0;
        locationY = Constants.TILE_SIZE/2.0;
        tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        images.set(imageIndex, op.filter(ObstructionManager.convertToBufferedImage( images.get(imageIndex)),null));
        rotations[imageIndex]++;
    }

    private int findTileByLocation(int row,int col){
        for(Tile tile : tiles){
            if(tile.getRow()==row && tile.getCol()==col){
                return tile.getTileId();
            }
        }
        return -1;
    }

    public int[] getTileImageNums(){
        int[] imageNums = new int[tiles.size()];
        for(int i = 0; i < imageNums.length;i++){
            imageNums[i] = tiles.get(i).getTileImageNum();
        }
        return imageNums;
    }

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public int getImageCount(){
        return images.size();
    }
    public int[] getRotations(){
        return rotations;
    }
}