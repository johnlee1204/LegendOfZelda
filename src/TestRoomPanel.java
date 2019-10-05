import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
public class TestRoomPanel extends JPanel
{
    private Player player;
    private long lastFrameTime;
    public TestRoomPanel(){
        addKeyListener(new KeyListenBoy());
        setFocusable(true);
        requestFocus();
        player = new Player(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,56,84,5,new ArrayList(),3);
        lastFrameTime = System.currentTimeMillis();
    }

    public void paintComponent(Graphics g){
        if(System.currentTimeMillis()-lastFrameTime>1.0/30){
            super.paintComponent(g);
            player.draw(g);
            //player.update();
            lastFrameTime = System.currentTimeMillis();
        }
        repaint();
    }
    public class KeyListenBoy implements KeyListener
    {
        public void keyReleased(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==37){
                player.setRight(true);
            }
            if(key==38){
                player.setDown(true);
            }
            if(key==39){
                player.setLeft(true);
            }
            if(key==40){
                player.setUp(true);
            }
        }

        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==37){
                player.setRight(false);
            }
            if(key==38){
                player.setDown(false);
            }
            if(key==39){
                player.setLeft(false);
            }
            if(key==40){
                player.setUp(false);
            }
        }

        public void keyTyped(KeyEvent e){
        }

    }
}