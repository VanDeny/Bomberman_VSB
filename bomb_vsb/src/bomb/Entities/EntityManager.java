package bomb.Entities;

import bomb.Entities.Creatures.Creature;
import bomb.Entities.Creatures.Enemy;
import bomb.Entities.Creatures.Player;
import bomb.Entities.Creatures.Static.Bomb.Bombs;
import bomb.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> comparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
                return -1;
            return 1;
        }
    };


    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        this.entities = new ArrayList<Entity>();
        entities.add(player);
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }
        entities.sort(comparator);
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        handler.getWorld().getEntityManager().getPlayer().lowerPlacedBomb();
    }

    public Creature getEntity(int x, int y) {

        for (Entity e : entities) {
            if (e instanceof Creature) {
                if (e.getX() >= x && e.getX() <= x+64 && e.getY() >= y && e.getY() <= y+64)
                    return (Creature) e;
            }
        }
        return null;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

}
