import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
public class MainFrame{
    private static JFrame frame; 
    private static LevelDesignPanel testRoomPanel; 
    private static Container frameContainer; 
    private static Dimension screenSize; 
    private static JMenuBar menuBar;
    private static JMenu menu,name;
    private static JMenuItem close,resetMap,saveMap,loadMap;
    public static void main(String[] args){
        frame = new JFrame(Constants.currentFileName);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Constants.SCREEN_WIDTH = (int)screenSize.getWidth();
        Constants.SCREEN_HEIGHT = (int)screenSize.getHeight();
        Constants.TILE_SIZE = Constants.SCREEN_WIDTH/40;
        frame.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        //TestRoomPanel testRoomPanel = new TestRoomPanel();
        testRoomPanel = new LevelDesignPanel();
        testRoomPanel.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        frameContainer = frame.getContentPane();
        frameContainer.add(testRoomPanel);
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        name = new JMenu("New File");
        close = new JMenuItem(new AbstractAction("Exit") {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

        saveMap = new JMenuItem(new AbstractAction("Save Map") {
                public void actionPerformed(ActionEvent e) {
                    ScreenManager screenManager = new ScreenManager(testRoomPanel.getTileManager(),testRoomPanel.getObstructionManager(),testRoomPanel.getDoorManager());
                    screenManager.save(testRoomPanel,screenManager.getScreens().get(0));
                }
            });

        loadMap = new JMenuItem(new AbstractAction("Load Map") {
                public void actionPerformed(ActionEvent e) {
                    ScreenManager screenManager = new ScreenManager(testRoomPanel.getTileManager(),testRoomPanel.getObstructionManager(),testRoomPanel.getDoorManager());
                    screenManager.load(testRoomPanel);
                }
            });
        resetMap = new JMenuItem(new AbstractAction("Reset Map") {
                public void actionPerformed(ActionEvent e) {
                    resetMap();
                }
            });
        menu.add(close);
        menu.add(saveMap);
        menu.add(loadMap);
        menu.add(resetMap);
        menuBar.add(menu);
        menuBar.add(name);
        frame.setJMenuBar(menuBar);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    private static void resetMap(){
        frameContainer.removeAll();
        testRoomPanel = new LevelDesignPanel();
        testRoomPanel.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        frameContainer.add(testRoomPanel);
    }

    public static void setFileTitle(String title){
        name.setText(title);
    }
    public static void minimizeWindow(){
        frame.setState(Frame.ICONIFIED);
    }
}