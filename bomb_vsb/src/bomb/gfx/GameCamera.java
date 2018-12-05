package bomb.gfx;

import bomb.Entities.Entity;
import bomb.Handler;
import bomb.tiles.Tile;

public class GameCamera {

    private Handler handler;

    private double xOffset, yOffset;

    public GameCamera(Handler handler, double xOffset, double yOffset)
    {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

    }

    public void checkBlankSpace() {

        if(xOffset < 0){
            xOffset = 0;
        }
        else if(xOffset > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth()){
            xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
        }

        if(yOffset < 0) {
            yOffset = 0;
        }
        else if(yOffset > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()){
            yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
        }

    }

    public void centerOnEntity(Entity e)
    {

        this.xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth()/2;
        this.yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight()/2;
        checkBlankSpace();
    }

    public void move(double xAmt, double yAmt)
    {
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
    }


    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
        checkBlankSpace();
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
        checkBlankSpace();
    }
}
