package bomb.Entities.Creatures.Static.Bomb;

import bomb.Entities.Creatures.Static.StaticEntity;
import bomb.Handler;
import bomb.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends StaticEntity {

    private boolean last;
    private BufferedImage explosion;
    private double x, y;
    private Handler handler;
    private double timer = 20;

    public Explosion(Handler handler, double x, double y,int direction, boolean last) {
        super(handler, x, y);
        this.last = last;

        this.x = x;
        this.y = y;
        this.handler = handler;
        switch (direction) {
            case 0:
                if(!last) {
                    explosion = Asset.bUpward;
                } else {
                    explosion = Asset.bUp;
                }
                break;
            case 1:
                if(!last) {
                    explosion = Asset.bSide;
                } else {
                    explosion = Asset.bRight;
                }
                break;
            case 2:
                if(!last) {
                    explosion = Asset.bUpward;
                } else {
                    explosion = Asset.bDown;
                }
                break;
            case 3:
                if(!last) {
                    explosion = Asset.bSide;
                } else {
                    explosion = Asset.bLeft;
                }
                break;
        }
    }

    @Override
    public void tick() {
        if(timer > 0)
            timer--;

        else{
            handler.getWorld().getEntityManager().removeEntity(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(explosion, (int)x, (int)y, width, height,null);
    }
}
