import java.awt.*;
import java.util.ArrayList;

public class Sword extends Weapon {

    private int range;
    private Rectangle leftSwingBox;
    private Rectangle downSwingBox;
    private Rectangle rightSwingBox;
    private Rectangle upSwingBox;

    public Sword(Image image, int imageNum, int value, int x, int y, int damage, int range) {
        super(image, imageNum, value, x, y, damage);
        this.range = range;
    }

    public void swing(int direction, Graphics g, ObstructionManager obm, EnemyManager enm) {

        int playerWidth = Constants.TILE_SIZE*5/4/2;
        int playerHeight = Constants.TILE_SIZE*3/2/2;
        this.leftSwingBox = new Rectangle(x - range + playerWidth,y - range / 2 + playerHeight*2,range,range/2);
        this.downSwingBox = new Rectangle(x + playerWidth ,y + playerHeight,range / 2, range);
        this.rightSwingBox = new Rectangle(x + playerWidth,y - range / 2 + playerHeight*2,range,range/2);
        this.upSwingBox = new Rectangle(x + playerWidth,y - range + playerHeight,range / 2,range);

        g.setColor(Color.PINK);
        ArrayList<Obstruction> obs = (ArrayList<Obstruction>) obm.getObstructions().clone();
        ArrayList<Enemy> enemies = (ArrayList<Enemy>) enm.getEnemies().clone();
        switch(direction) {
            case 0: //down
                for(Obstruction ob : obs) {
                    if(ob.getRect().intersects(this.downSwingBox)) {
                        obm.removeObstruction(ob.getX(), ob.getY());
                    }
                }

                for(Enemy enemy : enemies) {
                    if(enemy.getHitBox().intersects(this.downSwingBox)) {
                        enemy.damage(damage);
                    }
                }

                break;
            case 1: // left
                for(Obstruction ob : obs) {
                    if(ob.getRect().intersects(this.leftSwingBox)) {
                        obm.removeObstruction(ob.getX(), ob.getY());
                    }
                }

                for(Enemy enemy : enemies) {
                    if(enemy.getHitBox().intersects(this.leftSwingBox)) {
                        enemy.damage(damage);
                    }
                }
                break;
            case 2: // up
                for(Obstruction ob : obs) {
                    if(ob.getRect().intersects(this.upSwingBox)) {
                        obm.removeObstruction(ob.getX(), ob.getY());
                    }
                }

                for(Enemy enemy : enemies) {
                    if(enemy.getHitBox().intersects(this.upSwingBox)) {
                        enemy.damage(damage);
                    }
                }
                break;
            case 3: //right
                for(Obstruction ob : obs) {
                    if(ob.getRect().intersects(this.rightSwingBox)) {
                        obm.removeObstruction(ob.getX(), ob.getY());
                    }
                }

                for(Enemy enemy : enemies) {
                    if(enemy.getHitBox().intersects(this.rightSwingBox)) {
                        enemy.damage(damage);
                    }
                }
                break;
        }
    }

}
