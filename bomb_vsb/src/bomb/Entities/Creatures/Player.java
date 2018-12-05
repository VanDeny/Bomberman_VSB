package bomb.Entities.Creatures;

import bomb.Entities.Creatures.Static.Bomb.Bombs;
import bomb.Entities.Creatures.Static.Bomb.DirectionalExplosion;
import bomb.Entities.Creatures.Static.Bomb.Explosion;
import bomb.Entities.Creatures.Static.Stone;
import bomb.Entities.Entity;
import bomb.Handler;
import bomb.gfx.Asset;
import bomb.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private static int numberOfBombs = 1;
    private int numberOfPlaced = 0;

    private int deahtTimer = 0;

    private BufferedImage player = Asset.playerDown;

    private Handler handler;

    public Player(Handler handler, double x, double y) {
        super(handler, x, y, Creature.DEF_CREATURE_WIDTH, Creature.DEF_CREATURE_HEIGHT);

        this.handler = handler;
        bounds.x = 10;
        bounds.y = 20;
        bounds.width = 40;
        bounds.height = 34;

    }

    @Override
    public void tick() {
        if (isAlive) {
            getInput();
            move();
            handler.getGameCamera().centerOnEntity(this);
        } else {
            afterKill();
        }
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up)
            yMove = -speed;
        if (handler.getKeyManager().down)
            yMove = speed;
        if (handler.getKeyManager().left)
            xMove = -speed;
        if (handler.getKeyManager().right)
            xMove = speed;
        if (handler.getKeyManager().space && numberOfPlaced < numberOfBombs) {
            placeBomb();
        }
    }

    private void placeBomb() {
        numberOfPlaced += 1;
        int xt = (int) (this.x + 32) / Tile.TILE_WIDTH;
        int yt = (int) (this.y + 32) / Tile.TILE_HEIGHT;
        handler.getWorld().getEntityManager().addEntity(new Bombs(handler, xt * Tile.TILE_WIDTH, yt * Tile.TILE_HEIGHT));
    }

    public void lowerPlacedBomb() {
        this.numberOfPlaced = 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(player, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public boolean checkEntityCollision(double xOffset, double yOffset) {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0d, 0d).intersects(getCollisionBounds(xOffset, yOffset))) {
                if (e instanceof Enemy)
                    handler.getWorld().getEntityManager().getPlayer().kill();
                if (e instanceof Bombs)
                    continue;
                if (e instanceof Explosion)
                    this.kill();
                if (e instanceof Stone)
                    return true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterKill() {
        player = Asset.playerDeath;

        if (deahtTimer < 120)
            deahtTimer++;
        else {
            handler.getWorld().getEntityManager().removeEntity(this);
            handler.getGame().terminate();
        }

    }

    @Override
    public void kill() {
        if (!isAlive)
            return;

        isAlive = false;
    }
}
