package bomb.Entities.Creatures.Static;

import bomb.Entities.Entity;
import bomb.Handler;
import bomb.tiles.Tile;

public abstract class StaticEntity extends Entity {


    public StaticEntity(Handler handler, double x, double y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    }


}
