import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LevelDesignPanel extends JPanel {
    private long lastFrame;
    private int frameCount, mouseX, currentMouseObject, mouseY, currentTile, currentObstruction, currentEnemy;

    private TileManager tileManager;
    private ObstructionManager obstructionManager;
    private DoorManager doorManager;
    private EnemyManager enemyManager;

    private boolean ctrl = false, alt = false,shift = false;
    private boolean showHidden = true;
    private LevelDesignPanel levelDesignPanel = this;
    private Player player;
    private int playerSpeed = 10;
    Image healthHeart;
    {
        try {
            healthHeart = ImageIO.read(new File("images/ui/healthHeart.png")).getScaledInstance(Constants.TILE_SIZE, Constants.TILE_SIZE,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean swinging = false;

    public LevelDesignPanel() {
        addMouseListener(new MouseListenBoy());
        addMouseMotionListener(new MouseMotionListenerBoy());
        addKeyListener(new KeyListenBoy());
        addMouseWheelListener(new MouseWheelListenerBoy());
        setFocusable(true);
        requestFocus();
        tileManager = new TileManager();
        obstructionManager = new ObstructionManager();
        doorManager = new DoorManager();
        enemyManager = new EnemyManager();
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Constants.TILE_SIZE*5/4, Constants.TILE_SIZE*3/2, playerSpeed, new ArrayList(), 3);
        lastFrame = System.currentTimeMillis();
        currentMouseObject = 0;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (System.currentTimeMillis() - lastFrame > 1000.0 / Constants.MAX_FPS) {
            tileManager.update();
            enemyManager.update();
            lastFrame = System.currentTimeMillis();
            player.update(levelDesignPanel);
            if(healthHeart != null) {
                for(int i = 0; i < player.getHearts(); i++) {

                }
            }
        }
        tileManager.draw(g);
        if (currentMouseObject == 0) {
            tileManager.drawOnCursor(g, currentTile, mouseX, mouseY);
        }
        obstructionManager.draw(g,showHidden);
        if (currentMouseObject == 1) {
            obstructionManager.drawOnCursor(g, currentObstruction, mouseX, mouseY);
        }
        doorManager.draw(g);
        if (currentMouseObject == 2) {
            doorManager.drawOnCursor(g, mouseX, mouseY);
        }
        enemyManager.draw(g);
        if (currentMouseObject == 3) {
            enemyManager.drawOnCursor(g, currentEnemy, mouseX, mouseY);
        }
        player.draw(g);
        if(swinging) {
            player.swing(g, obstructionManager, enemyManager);
            swinging = ! swinging;
        }

        g.setColor(Color.BLACK);
        if (showHidden) {
            for (int i = 0; i <= Constants.SCREEN_WIDTH / Constants.TILE_SIZE; i++) {
                g.drawLine(i * Constants.TILE_SIZE, 0, i * Constants.TILE_SIZE, Constants.SCREEN_HEIGHT / Constants.TILE_SIZE * Constants.TILE_SIZE);
            }
            for (int i = 0; i <= Constants.SCREEN_HEIGHT / Constants.TILE_SIZE; i++) {
                g.drawLine(0, i * Constants.TILE_SIZE, Constants.SCREEN_WIDTH / Constants.TILE_SIZE * Constants.TILE_SIZE, i * Constants.TILE_SIZE);
            }
        }
        repaint();
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
               swinging = !swinging;
            }


            if (key >= 49 && key <= 52) {
                currentMouseObject = key - 49;
            }
            if(key == 16){
                shift = true;
                player.setSpeed(playerSpeed*5);
            }
            if (key == 17) {
                ctrl = true;
            }
            if (key == 18) {
                alt = true;
            }

            if (key == 37) { //array left
                player.setRight(false);
            }
            if (key == 38) { //array up
                player.setDown(false);
            }
            if (key == 39) { //array right
                player.setLeft(false);
            }
            if (key == 40) {//arrow down
                player.setUp(false);
            }

            if (key == 71) {//g
                showHidden = !showHidden;
            }
            if (key == 27) {//Escape
                System.exit(0);
            }
            if(key == 192){
                MainFrame.minimizeWindow();
            }
            if(key==82&&currentMouseObject==1){ //r
                obstructionManager.rotate(currentObstruction);
            }
            else if(key==82&&currentMouseObject==0){
                tileManager.rotate(currentTile);
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == 16){
                shift = false;
                player.setSpeed(playerSpeed);
            }
            if (key == 17) {
                ctrl = false;
            }
            if (key == 18) {
                alt = false;
            }
            if (key == 37) {
                player.setRight(true);
            }
            if (key == 38) {
                player.setDown(true);
            }
            if (key == 39) {
                player.setLeft(true);
            }
            if (key == 40) {
                player.setUp(true);
            }
        }

        public void keyTyped(KeyEvent e) {

        }
    }

    public class MouseListenBoy implements MouseListener {
        public void mouseExited(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            if (alt) {
                doorManager.getDoors().get(0).enter(levelDesignPanel);
            } else if (ctrl && currentMouseObject == 1) {
                obstructionManager.removeObstruction(mouseX, mouseY);
            } else if (currentMouseObject == 0) {
                tileManager.changeTile(currentTile, mouseY / Constants.TILE_SIZE, mouseX / Constants.TILE_SIZE);
            } else if (currentMouseObject == 1) {
                obstructionManager.addObstruction(currentObstruction, mouseX, mouseY);
            } else if (currentMouseObject == 2) {
                doorManager.addFirstDoor(levelDesignPanel,mouseY, mouseX);
            } else if(currentMouseObject == 3) {
                enemyManager.addEnemy(currentEnemy, mouseX, mouseY);
            }
        }

        public void mouseClicked(MouseEvent e) {
        }
    }

    public class MouseMotionListenerBoy implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            if (ctrl && currentMouseObject == 1) {
                obstructionManager.removeObstruction(mouseX, mouseY);
            } else if (currentMouseObject == 0) {
                tileManager.changeTile(currentTile, mouseY / Constants.TILE_SIZE, mouseX / Constants.TILE_SIZE);
            } else if (currentMouseObject == 1) {
                obstructionManager.addObstruction(currentObstruction, mouseX, mouseY);
            }
        }

        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
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