package bomb.tiles;

import bomb.gfx.Asset;

public class RockTile extends Tile {

    public RockTile(int id)
    {
        super(Asset.stone, id);
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

}
