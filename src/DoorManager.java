import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class DoorManager implements Serializable {
    private ArrayList<Door> doors;
    transient private ArrayList<Image> images;
    transient private ArrayList<Image> cursorImages;
    private String[] doorImageFiles = new String[]{"doorTopLeft.png","doorTopRight.png","doorBottomRight.png","doorBottomLeft.png"};
    private int doorId;
    public DoorManager(){
        doors = new ArrayList<>();
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        try{
            for(String doorImageFile : doorImageFiles){
                BufferedImage temp = ImageIO.read(new File("images/doors/"+doorImageFile));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE,Constants.TILE_SIZE,Image.SCALE_SMOOTH));
                cursorImages.add(temp.getScaledInstance(32,32,Image.SCALE_SMOOTH));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){
        for(Door door : doors){
            door.draw(g);
        }
    }

    public void drawOnCursor(Graphics g, int x, int y){
        g.drawImage(cursorImages.get(0),x,y,null);
        g.drawImage(cursorImages.get(1),x+32,y,null);
        g.drawImage(cursorImages.get(2),x+32,y+32,null);
        g.drawImage(cursorImages.get(3), x,y+32,null);
    }

    public void addDoor(int row, int col, File destinationFile){
        doors.add(new Door(doorId,images,row,col,destinationFile));
    }

    public void updateDoorImages(){
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        try{
            for(String doorImageFile : doorImageFiles){
                BufferedImage temp = ImageIO.read(new File("images/doors/"+doorImageFile));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE,Constants.TILE_SIZE,Image.SCALE_SMOOTH));
                cursorImages.add(temp.getScaledInstance(32,32,Image.SCALE_SMOOTH));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        for(Door door : doors){
            door.setImages(images);
        }
    }

    public void addFirstDoor(LevelDesignPanel panel,int mouseY, int mouseX){
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/maps/ser");
        chooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "SER", "ser");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            if (chooser.getSelectedFile() == null) {
                return;
            }
            addDoor(mouseY / Constants.TILE_SIZE, mouseX / Constants.TILE_SIZE, chooser.getSelectedFile());
        }
        ScreenManager screenManager = new ScreenManager(panel.getTileManager(),panel.getObstructionManager(), panel.getDoorManager());
        screenManager.save(panel,screenManager.getScreens().get(0));
    }

    public int getDoorId() {
        return doorId;
    }

    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }

    public ArrayList<Door> getDoors(){
        return doors;
    }
    public void setDoors(ArrayList<Door> doors){
        this.doors = doors;
    }
}
