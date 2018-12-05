package bomb.Entities.Creatures.Static;

import bomb.Handler;
import bomb.gfx.Asset;
import bomb.tiles.Tile;

import java.awt.*;

public class Stone extends StaticEntity {

    public Stone(Handler handler, double x, double y) {
        super(handler, x, y);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Asset.border, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

}
