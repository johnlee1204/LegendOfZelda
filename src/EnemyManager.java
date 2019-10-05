import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EnemyManager {
    private ArrayList<Enemy> enemies;
    private ArrayList<Image> images;
    private ArrayList<Image> cursorImages;
    private String[] imageFileNames;
    public EnemyManager() {
        enemies = new ArrayList<>();
        images = new ArrayList<>();
        cursorImages = new ArrayList<>();
        try{
            File obstructionFolder  = new File("images/sprites/enemies");
            imageFileNames = obstructionFolder.list();
            Arrays.sort(imageFileNames);
            for(String fileName : imageFileNames) {
                BufferedImage temp = ImageIO.read(new File("images/sprites/enemies/" + fileName));
                images.add(temp.getScaledInstance(Constants.TILE_SIZE, Constants.TILE_SIZE, Image.SCALE_SMOOTH));
                cursorImages.add(temp.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update() {
        ArrayList<Enemy> enemiesCopy = (ArrayList<Enemy>)enemies.clone();
        for(Enemy enemy : enemiesCopy) {
            if(enemy.getHealth() <= 0) {
                enemies.remove(enemy);
            }
        }
    }

    public void drawOnCursor(Graphics g, int enemy, int x, int y) {
        g.drawImage(cursorImages.get(enemy), x, y, null);
    }

    public void draw(Graphics g) {
        for(Enemy enemy : enemies) {
            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
        }
    }

    public void addEnemy(int enemyType, int x, int y){
        switch(enemyType) {
            case 0:
                enemies.add(new Penguin(images.get(enemyType), enemyType, x,y,100));
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public int getImageCount() {
        return images.size();
    }
}
