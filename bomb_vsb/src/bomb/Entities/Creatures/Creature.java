package bomb.Entities.Creatures;

import bomb.Entities.Creatures.Static.Bomb.Bombs;
import bomb.Entities.Creatures.Static.Bomb.DirectionalExplosion;
import bomb.Entities.Creatures.Static.Bomb.Explosion;
import bomb.Entities.Entity;
import bomb.Game;
import bomb.Handler;
import bomb.tiles.Tile;

public abstract class Creature extends Entity {

    public static final int DEF_HEALTH = 1;
    public static final double DEF_SPEED = 5;
    public static final int DEF_CREATURE_WIDTH = 50;
    public static final int DEF_CREATURE_HEIGHT = 60;

    protected int health;
    protected double speed;
    protected double xMove, yMove;
    protected boolean isAlive = false;

    public Creature(Handler handler, double x, double y, int width, int height) {
        super(handler, x, y, width, height);
        this.health = DEF_HEALTH;
        this.speed = DEF_SPEED;
        xMove = 0;
        yMove = 0;
        isAlive = true;
    }

    public void moveX() {
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            if (!collision(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collision(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            }
            else
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;


        } else if (xMove < 0) {
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
            if (!collision(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collision(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            } else
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;

        }

    }

    public void moveY() {
        if(yMove < 0){//Up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            if(!collision((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collision((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }
        }else if(yMove > 0){//Down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            if(!collision((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collision((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }
        }
    }

    public void move() {
        if (!checkEntityCollision(xMove, 0d))
            moveX();
        if (!checkEntityCollision(0d, yMove)) ;
        moveY();
    }

    public boolean checkEntityCollision(double xOffset, double yOffset)
    {
        for (Entity e: handler.getWorld().getEntityManager().getEntities())
        {
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0d,0d).intersects(getCollisionBounds(xOffset, yOffset))) {
                if(e instanceof Explosion)
                    this.kill();
                if(e instanceof Player)
                    handler.getWorld().getEntityManager().getPlayer().kill();
                if(e instanceof Bombs)
                    continue;
                return true;

            }
        }
        return false;
    }

    public void kill(){
        handler.getWorld().getEntityManager().removeEntity(this);
    }

    public abstract void afterKill();

    public boolean collision(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public double getxMove() {
        return xMove;
    }

    public void setxMove(double xMove) {
        this.xMove = xMove;
    }

    public double getyMove() {
        return yMove;
    }

    public void setyMove(double yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
