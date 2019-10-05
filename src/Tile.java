import java.awt.image.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.io.*;
public class Tile implements Serializable{
    private int row,col,tileId, rotation;
    transient private Image image;
    private int imageNum;
    private Rectangle rect;
    public Tile(int imageNum,Image image, int row, int col,int tileId){
        this.row = row;
        this.col = col;
        this.tileId = tileId;
        this.image = image;
        this.imageNum = imageNum;
        rect = new Rectangle(col*Constants.TILE_SIZE,row*Constants.TILE_SIZE,Constants.TILE_SIZE,Constants.TILE_SIZE);
    }
    
    public void draw(Graphics g){
        g.drawImage(image,col*Constants.TILE_SIZE,row*Constants.TILE_SIZE,null);
    }

    public void update(){

    }
    
    public void setTileImage(int imageNum,Image image){
        this.image = image;
        this.imageNum = imageNum;
    }

    public void setRotation(int rotation){
        this.rotation = rotation;
    }

    public void updateRotation(){
        for(int i = 0; i < rotation; i++){
            this.rotate();
        }
    }

    public void rotate() {
        // The required drawing location
        double rotationRequired = Math.toRadians(90);
        double locationX = Constants.TILE_SIZE / 2.0;
        double locationY = Constants.TILE_SIZE / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(ObstructionManager.convertToBufferedImage(image),null);
    }


    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getTileId(){
        return tileId;
    }

    public int getTileImageNum(){
        return imageNum;
    }
    public int getRotation(){
        return rotation;
    }
}