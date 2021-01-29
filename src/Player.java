import java.awt.image.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
public class Player{
    private Image playerImage;
    private int x,y,width,height,speed,hearts,leftTypeNum,rightTypeNum,upTypeNum,downTypeNum;
    private ArrayList inventory;
    private boolean up = true,right = true, left = true, down = true,rightType = false,leftType = false,upType = false,downType = false;
    private Rectangle rect;
    ArrayList<Image> images;
    private Sword sword;
    private int direction;
    private boolean swingOnNextFrame;

    public Player(int x, int y,int width, int height,int speed, ArrayList inventory, int hearts){

        images = new ArrayList<>();
        try{
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordForwardRight.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordForwardLeft.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkBackwardRight.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkBackwardLeft.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordLeftBack.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordLeftFront.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordRightBack.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
            images.add((ImageIO.read(new File("images/sprites/link/linkSwordRightFront.png")).getScaledInstance(width,height,Image.SCALE_SMOOTH)));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.playerImage = images.get(0);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.inventory = inventory;
        this.hearts = hearts;
        rect = new Rectangle(x+(int)(10*Constants.TILE_SIZE/48.0),(int)(y+(height*4/5.0)),width-(int)(20*Constants.TILE_SIZE/48.0),(int)(height*(1/5.0))-(int)(5*Constants.TILE_SIZE/48.0));

        this.sword = new Sword(images.get(0), 99,100, x, y, 50, 100);
    }

    public void draw(Graphics g){
        g.drawImage(playerImage,x,y,null);
        //g.setColor(Color.BLUE);
        //g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
    }

    public void update(LevelDesignPanel levelDesignPanel){

        ArrayList<Door> doors = levelDesignPanel.getDoorManager().getDoors();
        for(Door door : doors){
            if (rect.intersects(door.getRect())){
                door.enter(levelDesignPanel);
            }
        }
        boolean keepGoing = true;
        if(up){
            if(!down){
                ArrayList<Obstruction> obstructions = levelDesignPanel.getObstructionManager().getObstructions();
                for(Obstruction obstruction : obstructions){
                    Rectangle temp = obstruction.getRect();
                    if(rect.intersects(new Rectangle((int)temp.getX(),(int)temp.getY()+speed,(int)temp.getWidth(),(int)temp.getHeight()))){
                        keepGoing = false;
                    }
                }
                if(keepGoing){rect.setLocation((int)rect.getX(),(int)(rect.getY()-speed));}
            }
            if(keepGoing){y-=speed;}
        }
        keepGoing = true;
        if(right){

            if(!left){
                ArrayList<Obstruction> obstructions = levelDesignPanel.getObstructionManager().getObstructions();
                for(Obstruction obstruction : obstructions){
                    Rectangle temp = obstruction.getRect();
                    if(rect.intersects(new Rectangle((int)temp.getX()-speed,(int)temp.getY(),(int)temp.getWidth(),(int)temp.getHeight()))){
                        keepGoing = false;
                    }
                }
                if(keepGoing){rect.setLocation((int)rect.getX()+speed,(int)rect.getY());}
            }
            if(keepGoing){x+=speed;}
        }
        keepGoing = true;
        if(down){
            if(!up){
                ArrayList<Obstruction> obstructions = levelDesignPanel.getObstructionManager().getObstructions();
                for(Obstruction obstruction : obstructions){
                    Rectangle temp = obstruction.getRect();
                    if(rect.intersects(new Rectangle((int)temp.getX(),(int)temp.getY()-speed,(int)temp.getWidth(),(int)temp.getHeight()))){
                        keepGoing = false;
                    }
                }
                if(keepGoing){rect.setLocation((int)rect.getX(),(int)(rect.getY()+speed));}
            }
            if(keepGoing){y+=speed;}
        }
        keepGoing = true;
        if(left){
            if(!right){
                ArrayList<Obstruction> obstructions = levelDesignPanel.getObstructionManager().getObstructions();
                for(Obstruction obstruction : obstructions){
                    Rectangle temp = obstruction.getRect();
                    if(rect.intersects(new Rectangle((int)temp.getX()+speed,(int)temp.getY(),(int)temp.getWidth(),(int)temp.getHeight()))){
                        keepGoing = false;
                    }
                }
                if(keepGoing){rect.setLocation((int)rect.getX()-speed,(int)rect.getY());}
            }
            if(keepGoing){x-=speed;}
        }
        this.sword.setX(this.x);
        this.sword.setY(this.y);
        if(up&&!down){
            direction = 2;
            if(!upType) {
                this.playerImage = images.get(2);
            }
            else{
                this.playerImage = images.get(3);
            }
            if(upTypeNum%10==0) {
                upType = !upType;
            }
            upTypeNum++;
        }
        else if(down&&!up){
            direction = 0;
            if(!downType) {
                this.playerImage = images.get(0);
            }
            else{
                this.playerImage = images.get(1);
            }
            if(downTypeNum%10==0) {
                downType = !downType;
            }
            downTypeNum++;
        }
        else if(right&&!left){
            direction = 3;
            if(!rightType) {
                this.playerImage = images.get(6);
            }
            else{
                this.playerImage = images.get(7);
            }
            if(rightTypeNum%10==0) {
                rightType = !rightType;
            }
            rightTypeNum++;
        }
        else if(left&&!right){
            direction = 1;
            if(!leftType) {
                this.playerImage = images.get(4);
            }
            else{
                this.playerImage = images.get(5);
            }
            if(leftTypeNum%10==0) {
                leftType = !leftType;
            }
            leftTypeNum++;
        }
    }

    public void setUp(boolean up){
        this.up = up;
    }

    public void setRight(boolean right){
        this.right = right;
    }

    public void setDown(boolean down){
        this.down = down;
    }

    public void setLeft(boolean left){
        this.left = left;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setRect(int x, int y){
        rect = new Rectangle(x+(int)(10*Constants.TILE_SIZE/48.0),(int)(y+(height*4/5.0)),width-(int)(20*Constants.TILE_SIZE/48.0),(int)(height*(1/5.0))-(int)(5*Constants.TILE_SIZE/48.0));
    }

    public void swing(Graphics g, ObstructionManager obm, EnemyManager enm) {
        this.sword.swing(this.direction, g, obm, enm);
    }

    public boolean getSwingOnNextFrame() {
        return swingOnNextFrame;
    }

    public void setSwingOnNextFrame(boolean swingOnNextFrame) {
        this.swingOnNextFrame = swingOnNextFrame;
    }

    public int getHearts() {
        return this.hearts;
    }
}