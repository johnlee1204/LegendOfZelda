import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ScreenManager {
    private ArrayList<Screen> screens;
    public ScreenManager(TileManager tileManager, ObstructionManager obstructionManager, DoorManager doorManager){
        screens = new ArrayList<>();
        screens.add(new Screen("testMap.ser",tileManager,obstructionManager,doorManager));
    }

    public void save(LevelDesignPanel levelDesignPanel,Screen screen){
        String fileName;
        if(Constants.currentFileName.length()==0) {
            fileName = JOptionPane.showInputDialog("Map Name");
            if(fileName == null || fileName.length() == 0) {
                JOptionPane.showMessageDialog(levelDesignPanel, "You Must Enter A Map Name To Save");
                return;
            }
        }
        else {
            fileName = Constants.currentFileName;
        }
        try{
            File file;
            File imageFile;
            if(fileName==null){
                file = new File("maps/ser/map.ser");
                imageFile= new File("maps/image/map.png");
            }
            else {
                file = new File("maps/ser/" + fileName + ".ser");
                imageFile= new File("maps/image/"+fileName+".png");
            }
            FileOutputStream opf = new FileOutputStream(file);
            ObjectOutputStream oop = new ObjectOutputStream(opf);
            oop.writeObject(screen);
            if(fileName.contains(".ser")) {
                Constants.currentFileName = fileName.substring(0, fileName.length() - 4);
            }
            else{
                Constants.currentFileName = fileName;
            }
            oop.close();
            opf.close();
            MainFrame.setFileTitle(Constants.currentFileName);


            if (!imageFile.exists()){
                imageFile.createNewFile();
            }
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(levelDesignPanel.getLocationOnScreen().x, levelDesignPanel.getLocationOnScreen().y, levelDesignPanel.getWidth(), levelDesignPanel.getHeight()));
            ImageIO.write(image, "png", imageFile);
        } catch(IOException exception){
            exception.printStackTrace();
        }
        catch(AWTException e){
            e.printStackTrace();
        }
    }

    public void load(LevelDesignPanel levelDesignPanel){
        if(Constants.currentFileName.length()>0) {
            save(levelDesignPanel, screens.get(0));
        }
        try{
            JFileChooser chooser = new JFileChooser();
            File workingDirectory = new File(System.getProperty("user.dir")+"/maps/ser");
            chooser.setCurrentDirectory(workingDirectory);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "SER", "ser");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                FileInputStream opf = new FileInputStream(chooser.getSelectedFile());
                ObjectInputStream oop = new ObjectInputStream(opf);
                Screen loadedScreen = ((Screen)oop.readObject());
                levelDesignPanel.setObstructionManager(loadedScreen.getObstructionManager());
                levelDesignPanel.getObstructionManager().setObstructionId(loadedScreen.getObstructionManager().getObstructionId());
                levelDesignPanel.getObstructionManager().updateImagesAfterLoad();

                levelDesignPanel.setTileManager(loadedScreen.getTileManager());
                levelDesignPanel.getTileManager().updateImagesAfterLoad();
                levelDesignPanel.getTileManager().updateImageRotationsAfterLoad();
                levelDesignPanel.getDoorManager().setDoors(loadedScreen.getDoorManager().getDoors());
                levelDesignPanel.getDoorManager().updateDoorImages();
                Constants.currentFileName = chooser.getSelectedFile().getName().substring(0,chooser.getSelectedFile().getName().length()-4);
                oop.close();
                opf.close();
                MainFrame.setFileTitle(Constants.currentFileName);

                if(loadedScreen.getDoorManager().getDoors().size()>0) {
                    Door door = loadedScreen.getDoorManager().getDoors().get(0);
                    levelDesignPanel.getPlayer().setLocation(door.getRect().x,door.getRect().y+Constants.TILE_SIZE);
                    levelDesignPanel.getPlayer().setRect(door.getRect().x,door.getRect().y+Constants.TILE_SIZE);
                } else {
                    levelDesignPanel.getPlayer().setLocation(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT+Constants.TILE_SIZE);
                    levelDesignPanel.getPlayer().setRect(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT+Constants.TILE_SIZE);
                }
            }

            //File file = new File("testMap.ser");

        } catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }
    public void loadToPlaceDoor(LevelDesignPanel levelDesignPanel,File file){
        try{
            FileInputStream opf = new FileInputStream(file);
            ObjectInputStream oop = new ObjectInputStream(opf);
            Screen loadedScreen = ((Screen)oop.readObject());
            levelDesignPanel.getObstructionManager().setObstructions(loadedScreen.getObstructionManager().getObstructions());
            levelDesignPanel.getObstructionManager().setObstructionId(loadedScreen.getObstructionManager().getObstructionId());
            
            
            levelDesignPanel.getDoorManager().setDoors(loadedScreen.getDoorManager().getDoors());
            levelDesignPanel.getDoorManager().updateDoorImages();
            Constants.currentFileName = file.getName().substring(0,file.getName().length()-4);
            oop.close();
            opf.close();
            MainFrame.setFileTitle(Constants.currentFileName);

            //File file = new File("testMap.ser");

        } catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }
    public ArrayList<Screen> getScreens(){
        return screens;
    }
}
