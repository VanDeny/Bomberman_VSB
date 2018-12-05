package bomb.Entities.Creatures.Static.Bomb;

import bomb.Entities.Creatures.Creature;
import bomb.Entities.Creatures.Static.StaticEntity;
import bomb.Entities.Entity;
import bomb.Handler;

import java.awt.*;

public class DirectionalExplosion extends StaticEntity {

    private int timeAfterExplosion = 20;
    private int direction, length, x, y;
    private Handler handler;
    private Explosion explosion[];

    public DirectionalExplosion(Handler handler, double x, double y, int direction, int length) {
        super(handler, x, y);
        this.x = (int)x;
        this.y = (int)y;
        this.length = length;
        this.direction = direction;
        this.handler = handler;
        this.explosion = new Explosion[1];

        createExplosions();
    }


    private void createExplosions() {
        int x = this.x;
        int y = this.y;
        for (int i = 0; i < explosion.length; i++) {
            boolean last;
            if (i == (explosion.length - 1)) last = true;
            else last = false;

            switch (direction) {
                case 0: y--; break;
                case 1: x++; break;
                case 2: y++; break;
                case 3: x--; break;
            }
            explosion[i] = new Explosion(handler,x , y, direction, last);
        }
    }


    @Override
    public void tick() {
        if(timeAfterExplosion > 0)
            timeAfterExplosion--;
        else
            handler.getWorld().getEntityManager().removeEntity(this);
    }

    @Override
    public void render(Graphics g) {
    }
}