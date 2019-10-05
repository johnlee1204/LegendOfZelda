import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Door implements Serializable {
    private int row,col,doorId,doorSetIdiok;
    transient private ArrayList<Image> images;
    private File destinationFile;
    private Rectangle rect;
    private static final long serialVersionUID = -1203855349170351799L;
    public Door(int doorId, ArrayList<Image> images, int row, int col, File destinationFile){
        this.doorId = doorId;
        this.images = images;
        this.row = row;
        this.col = col;
        this.destinationFile = destinationFile;
        this.rect = new Rectangle(col*Constants.TILE_SIZE,row*Constants.TILE_SIZE,Constants.TILE_SIZE*2,Constants.TILE_SIZE*2);
    }

    public void draw(Graphics g){
        g.drawImage(images.get(0),col*Constants.TILE_SIZE,row*Constants.TILE_SIZE,null);
        g.drawImage(images.get(1),(col+1)*Constants.TILE_SIZE,row*Constants.TILE_SIZE,null);
        g.drawImage(images.get(2),(col+1)*Constants.TILE_SIZE,(row+1)*Constants.TILE_SIZE,null);
        g.drawImage(images.get(3),col*Constants.TILE_SIZE,(row+1)*Constants.TILE_SIZE,null);
    }

    public void enter(LevelDesignPanel levelDesignPanel){
        try{
            FileInputStream opf = new FileInputStream(destinationFile);
            ObjectInputStream oop = new ObjectInputStream(opf);
            Screen loaded = null;
            try {loaded = ((Screen)oop.readObject());}catch (Exception e) {e.printStackTrace();}
            levelDesignPanel.setObstructionManager(loaded.getObstructionManager());
            levelDesignPanel.getObstructionManager().setObstructionId(loaded.getObstructionManager().getObstructionId());
            levelDesignPanel.getObstructionManager().updateImagesAfterLoad();

            levelDesignPanel.setTileManager(loaded.getTileManager());
            levelDesignPanel.getTileManager().updateImagesAfterLoad();
            levelDesignPanel.getTileManager().updateImageRotationsAfterLoad();
            //change when we add doors to more rooms
            levelDesignPanel.getDoorManager().setDoors(loaded.getDoorManager().getDoors());
            levelDesignPanel.getDoorManager().updateDoorImages();
            Constants.currentFileName = destinationFile.getName().substring(0,destinationFile.getName().length()-4);
            oop.close();
            opf.close();
            MainFrame.setFileTitle(Constants.currentFileName);
            Door door = loaded.getDoorManager().getDoors().get(0);

            levelDesignPanel.getPlayer().setLocation(door.getRect().x,door.getRect().y+Constants.TILE_SIZE);
            levelDesignPanel.getPlayer().setRect(door.getRect().x,door.getRect().y+Constants.TILE_SIZE);
            levelDesignPanel.getPlayer().setDown(true);
            levelDesignPanel.getPlayer().setUp(false);
            levelDesignPanel.getPlayer().update((levelDesignPanel));
            levelDesignPanel.getPlayer().setUp(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Rectangle getRect(){
        return rect;
    }

    public void setImages(ArrayList<Image> images){
        this.images = images;
    }
}
