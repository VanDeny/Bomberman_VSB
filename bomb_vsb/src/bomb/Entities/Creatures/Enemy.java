package bomb.Entities.Creatures;

import bomb.Entities.Creatures.Static.Bomb.Bombs;
import bomb.Entities.Creatures.Static.Bomb.DirectionalExplosion;
import bomb.Entities.Creatures.Static.Bomb.Explosion;
import bomb.Entities.Entity;
import bomb.Handler;
import bomb.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Creature {

    private int timer = 120;
    private boolean isAlive = false;
    private double xS, yS = 0;
    private int points = 100;
    private BufferedImage ballon = Asset.enemy;

    public Enemy(Handler handler, double x, double y) {
        super(handler, x, y, Creature.DEF_CREATURE_WIDTH, Creature.DEF_CREATURE_HEIGHT);
        isAlive = true;
        speed = 2;
    }

    @Override
    public void tick() {
        moveBallon();
        move();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Asset.enemy, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void moveBallon()
    {

        if(timer != 0)
            timer--;
        else {
            Random i = new Random();
            int j = i.nextInt(100) + 1;
            if (j < 50)
                xS = -speed;
            else
                xS = speed;

            xMove = xS;
            i = new Random();
            j = i.nextInt(100) + 1;
            if (j > 50)
                yS = -speed;
            else
                yS = speed;

            yMove = yS;
        timer = 120;
        }

        xMove = xS;
        yMove = yS;

        Creature player = handler.getWorld().getEntityManager().getPlayer();
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    @Override
    public void afterKill() {

        handler.getGame().addPoints(points);
        handler.getWorld().getEntityManager().removeEntity(this);
    }

    @Override
    public void kill() {
        if(!isAlive)
            afterKill();


        isAlive = false;
        while(true) {
            Random random = new Random();
            int x = random.nextInt(11) + 1;
            int y = random.nextInt(19) + 1;
            if (!(handler.getWorld().getTile(y,x).getId() != 0 || (handler.getWorld().getEntityManager().getPlayer().getX() == x && handler.getWorld().getEntityManager().getPlayer().getY() == y))) {
                break;
            }
        }
        handler.getWorld().getEntityManager().addEntity(new Enemy(handler,y*64,x*64));

    }
}
