package bomb.States;

import bomb.Entities.Creatures.Player;
import bomb.Entities.Creatures.Static.Stone;
import bomb.Handler;
import bomb.Worlds.World;


import java.awt.Graphics;

public class GameState extends State{

    private World world;

    public GameState(Handler handler){
        super(handler);
        world = new World(handler, "res/World/World.txt");
        this.handler.setWorld(world);


    }

    @Override
    public void tick(){
        world.tick();
    }

    @Override
    public void render(Graphics g){
        world.render(g);
    }

}
