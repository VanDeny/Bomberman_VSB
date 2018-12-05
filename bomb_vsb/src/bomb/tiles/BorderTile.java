package bomb.tiles;


import bomb.gfx.Asset;

public class BorderTile extends Tile {

    public BorderTile(int id)
    {
        super(Asset.border, id);
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

}
