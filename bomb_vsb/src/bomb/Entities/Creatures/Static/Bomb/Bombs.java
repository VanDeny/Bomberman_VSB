package bomb.Entities.Creatures.Static.Bomb;

import bomb.Entities.Creatures.Creature;
import bomb.Entities.Creatures.Static.StaticEntity;
import bomb.Handler;
import bomb.gfx.Asset;
import bomb.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bombs extends StaticEntity {

    protected double timeToExplode = 120;
    protected double timeAfterExplosion = 20;
    protected boolean isExploded = false;
    private double x, y;
    private DirectionalExplosion dExplosion[];

    private BufferedImage bomb = Asset.bomb;

    public Bombs(Handler handler, double x, double y) {
        super(handler, x, y);
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        if (timeToExplode > 0)
            timeToExplode--;
        else {
            if (!isExploded)
                explosion();

            if (timeAfterExplosion > 0)
                timeAfterExplosion--;

            else
                remove();
        }

    }

    private void explosion() {
        isExploded = true;

        bomb = Asset.bCenter;

        Creature a = handler.getWorld().getEntityManager().getEntity((int) x, (int) y);

        if (a != null)
            a.kill();

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        for (int i = 0; i < 4; i++) {
            handler.getWorld().getEntityManager().addEntity(new Explosion(handler, (x  + (dx[i] * Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()), (y  + (dy[i] * Tile.TILE_HEIGHT)- handler.getGameCamera().getyOffset()), i, true));
        }


    }

    public void remove() {
        handler.getWorld().getEntityManager().getPlayer().lowerPlacedBomb();
        handler.getWorld().getEntityManager().removeEntity(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bomb, (int) (this.x - handler.getGameCamera().getxOffset()), (int) (this.y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
