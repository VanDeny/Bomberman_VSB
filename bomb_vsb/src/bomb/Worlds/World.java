package bomb.Worlds;

import bomb.Entities.Creatures.Enemy;
import bomb.Entities.Creatures.Player;
import bomb.Entities.EntityManager;
import bomb.Handler;
import bomb.Utils.Utils;
import bomb.tiles.Tile;
import bomb.Entities.Creatures.Static.Stone;

import java.awt.Graphics;
import java.util.Random;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;

    private EntityManager entityManager;

    public World(Handler handler, String path)
    {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 64, 64));

        loadWorld(path);
        Random random = new Random();

        for (int i = 0; i < 15; i++)
        {
            int x = random.nextInt(20) + 1;
            int y = random.nextInt(12) + 1;

            if(tiles[x][y] != 0 || (x == 1 && y == 1) || (x == 1 && y == 2) || (x == 2 && y == 1)) {
                if (i != 0)
                    i--;
                continue;
            }
            tiles[x][y] = 2;
            entityManager.addEntity(new Stone(handler, x * 64, y * 64));
        }

        for (int i = 0; i < 7; i++) {
            int x = random.nextInt(12) + 1;
            int y = random.nextInt(20) + 1;
            if( tiles[y][x] != 0 || (x == 1 && y == 1)) {
                if( i != 0)
                    i--;
                continue;
            }
            entityManager.addEntity(new Enemy(handler,x*Tile.TILE_WIDTH,y*Tile.TILE_HEIGHT));
        }

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void loadWorld(String path)
    {

        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                tiles[x][y] = Utils.parseInt(tokens[(x+y * width) + 4]);
            }
        }
    }

    public void tick()
    {
        entityManager.tick();
    }

    public void setTile(int x, int y)
    {

    }

    public Tile getTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
            return Tile.grassTile;

        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
            return Tile.rockTile;

        return t;
    }

    public void render(Graphics g)
    {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH +1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);;
        int yEnd = (int) Math.min(height,(handler.getGameCamera().getyOffset() + handler.getWidth()) / Tile.TILE_HEIGHT +1);

        for (int x = xStart; x < xEnd; x++)
        {
            for (int y = yStart; y < yEnd; y++)
            {
                getTile(x,y).render(g,(int) (x* Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int)(y*Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        entityManager.render(g);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
