import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LevelDesignPanel extends JPanel {
    private long timeAtLastFrame;
    private int currentMouseObject = 0, currentTile, currentObstruction, currentEnemy;
    private Point cursorLocation = new Point(0,0);
    private TileManager tileManager;
    private ObstructionManager obstructionManager;
    private DoorManager doorManager;
    private EnemyManager enemyManager;

    private boolean ctrl = false, alt = false;
    private boolean showHidden = true;
    private Player player;
    private int playerSpeed = 10;
    private Image healthHeart;


    public LevelDesignPanel() {
        addListeners();
        focusCurrentPanel();
        createManagers();
        createPlayer();
        loadHeartImage();
        timeAtLastFrame = System.currentTimeMillis();
    }

    private void addListeners() {
        addMouseListener(new MouseListenBoy(this));
        addMouseMotionListener(new MouseMotionListenerBoy());
        addKeyListener(new KeyListenBoy());
        addMouseWheelListener(new MouseWheelListenerBoy());
    }

    private void focusCurrentPanel() {
        setFocusable(true);
        requestFocus();
    }

    private void createManagers() {
        tileManager = new TileManager();
        obstructionManager = new ObstructionManager();
        doorManager = new DoorManager();
        enemyManager = new EnemyManager();
    }

    private void createPlayer() {
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Constants.TILE_SIZE*5/4, Constants.TILE_SIZE*3/2, playerSpeed, new ArrayList(), 3);
    }

    private void loadHeartImage() {
        try {
            healthHeart = ImageIO.read(new File("images/ui/healthHeart.png")).getScaledInstance(Constants.TILE_SIZE, Constants.TILE_SIZE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateManagersOnNewFrame();

        tileManager.draw(g);
        drawSelectedCursorObject(g);
        player.draw(g);

        drawHealthHearts(g);

        if(player.getSwingOnNextFrame()) {
            player.swing(g, obstructionManager, enemyManager);
            player.setSwingOnNextFrame(false);
        }

        g.setColor(Color.BLACK);
        if (showHidden) {
            showGridLines(g);
        }
        repaint();
    }

    private void updateManagersOnNewFrame() {
        if (System.currentTimeMillis() - timeAtLastFrame > 1000.0 / Constants.MAX_FPS) {
            tileManager.update();
            enemyManager.update();
            timeAtLastFrame = System.currentTimeMillis();
            player.update(this);
        }
    }

    private void drawHealthHearts(Graphics g) {
        if(healthHeart != null) {
            for(int i = 0; i < player.getHearts(); i++) {
                g.drawImage(healthHeart, 20 + Constants.TILE_SIZE * i, Constants.SCREEN_HEIGHT / 50, null);
            }
        }
    }

    private void drawSelectedCursorObject(Graphics g) {
        if (currentMouseObject == 0) {
            tileManager.drawOnCursor(g, currentTile, (int)cursorLocation.getX(), (int)cursorLocation.getY());
        }
        obstructionManager.draw(g,showHidden);
        if (currentMouseObject == 1) {
            obstructionManager.drawOnCursor(g, currentObstruction, (int)cursorLocation.getX(), (int)cursorLocation.getY());
        }
        doorManager.draw(g);
        if (currentMouseObject == 2) {
            doorManager.drawOnCursor(g, (int)cursorLocation.getX(), (int)cursorLocation.getY());
        }
        enemyManager.draw(g);
        if (currentMouseObject == 3) {
            enemyManager.drawOnCursor(g, currentEnemy, (int)cursorLocation.getX(), (int)cursorLocation.getY());
        }
    }

    private void showGridLines(Graphics g) {
        for (int i = 0; i <= Constants.SCREEN_WIDTH / Constants.TILE_SIZE; i++) {
            g.drawLine(i * Constants.TILE_SIZE, 0, i * Constants.TILE_SIZE, Constants.SCREEN_HEIGHT / Constants.TILE_SIZE * Constants.TILE_SIZE);
        }
        for (int i = 0; i <= Constants.SCREEN_HEIGHT / Constants.TILE_SIZE; i++) {
            g.drawLine(0, i * Constants.TILE_SIZE, Constants.SCREEN_WIDTH / Constants.TILE_SIZE * Constants.TILE_SIZE, i * Constants.TILE_SIZE);
        }
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public ObstructionManager getObstructionManager() {
        return obstructionManager;
    }

    public DoorManager getDoorManager() {
        return doorManager;
    }
    
    public void setObstructionManager(ObstructionManager obstructionManager){
        this.obstructionManager = obstructionManager;
    }
    public Player getPlayer(){
        return player;
    }
    
    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
    }

    public class KeyListenBoy implements KeyListener {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == 32) {
               player.setSwingOnNextFrame(true);
            }


            if (key >= 49 && key <= 52) {
                currentMouseObject = key - 49;
            }
            if(key == KeyEvent.VK_SHIFT){
                player.setSpeed(playerSpeed*5);
            }
            if (key == KeyEvent.VK_CONTROL) {
                ctrl = true;
            }
            if (key == KeyEvent.VK_ALT) {
                alt = true;
            }

            if (key == KeyEvent.VK_LEFT) {
                player.setRight(false);
            }
            if (key == KeyEvent.VK_UP) {
                player.setDown(false);
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setLeft(false);
            }
            if (key == KeyEvent.VK_DOWN) {
                player.setUp(false);
            }

            if (key == KeyEvent.VK_G) {//g
                showHidden = !showHidden;
            }
            if (key == KeyEvent.VK_ESCAPE) {//Escape
                System.exit(0);
            }
            if(key == KeyEvent.VK_BACK_SLASH){
                MainFrame.minimizeWindow();
            }
            if(key == KeyEvent.VK_R && currentMouseObject==1){ //r
                obstructionManager.rotate(currentObstruction);
            }
            else if(key == KeyEvent.VK_R && currentMouseObject==0){
                tileManager.rotate(currentTile);
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SHIFT){
                player.setSpeed(playerSpeed);
            }
            if (key == KeyEvent.VK_CONTROL) {
                ctrl = false;
            }
            if (key == KeyEvent.VK_ALT) {
                alt = false;
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setRight(true);
            }
            if (key == KeyEvent.VK_UP) {
                player.setDown(true);
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setLeft(true);
            }
            if (key == KeyEvent.VK_DOWN) {
                player.setUp(true);
            }
        }

        public void keyTyped(KeyEvent e) {

        }
    }

    public class MouseListenBoy implements MouseListener {
        private LevelDesignPanel levelDesignPanel;
        public MouseListenBoy(LevelDesignPanel levelDesignPanel) {
            this.levelDesignPanel = levelDesignPanel;
        }
        public void mouseExited(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            cursorLocation.setLocation(e.getX(), e.getY());

            if (alt) {
                doorManager.getDoors().get(0).enter(levelDesignPanel);
            } else if (ctrl && currentMouseObject == 1) {
                obstructionManager.removeObstruction((int)cursorLocation.getX(), (int)cursorLocation.getY());
            } else if (currentMouseObject == 0) {
                tileManager.changeTile(currentTile, (int)cursorLocation.getY() / Constants.TILE_SIZE, (int)cursorLocation.getX() / Constants.TILE_SIZE);
            } else if (currentMouseObject == 1) {
                obstructionManager.addObstruction(currentObstruction, (int)cursorLocation.getX(), (int)cursorLocation.getY());
            } else if (currentMouseObject == 2) {
                doorManager.addFirstDoor(levelDesignPanel,(int)cursorLocation.getY(), (int)cursorLocation.getX());
            } else if(currentMouseObject == 3) {
                enemyManager.addEnemy(currentEnemy, (int)cursorLocation.getX(), (int)cursorLocation.getY());
            }
        }

        public void mouseClicked(MouseEvent e) {
        }
    }

    public class MouseMotionListenerBoy implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
            cursorLocation.setLocation(e.getX(), e.getY());
            if (ctrl && currentMouseObject == 1) {
                obstructionManager.removeObstruction(cursorLocation.x, cursorLocation.y);
            } else if (currentMouseObject == 0) {
                tileManager.changeTile(currentTile, cursorLocation.y / Constants.TILE_SIZE, cursorLocation.x / Constants.TILE_SIZE);
            } else if (currentMouseObject == 1) {
                obstructionManager.addObstruction(currentObstruction, cursorLocation.x, cursorLocation.y);
            }
        }

        public void mouseMoved(MouseEvent e) {
            cursorLocation.setLocation(e.getX(), e.getY());
        }
    }

    public class MouseWheelListenerBoy implements MouseWheelListener {

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (currentMouseObject == 0) {
                currentTile = Math.abs((currentTile + (int) (e.getPreciseWheelRotation())) % tileManager.getImageCount());
            } else if (currentMouseObject == 1) {
                currentObstruction = Math.abs((currentObstruction + ((int) (e.getPreciseWheelRotation()))) % obstructionManager.getImageCount());
            } else if (currentMouseObject == 2) {
            } else if(currentMouseObject == 3) {
                currentEnemy = Math.abs((currentEnemy + ((int) (e.getPreciseWheelRotation()))) % enemyManager.getImageCount());
            }
        }
    }
}