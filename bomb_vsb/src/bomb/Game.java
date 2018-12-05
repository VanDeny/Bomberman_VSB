package bomb;

import bomb.Display.Display;
import bomb.Input.KeyManager;
import bomb.States.GameState;
import bomb.States.State;
import bomb.Utils.Database;
import bomb.gfx.Asset;
import bomb.gfx.GameCamera;

import java.awt.*;
import java.awt.image.BufferStrategy;


import static javax.swing.JOptionPane.showMessageDialog;


public class Game implements Runnable {

    private Display display;
    private int width, height;
    public String title;
    private Thread thread;

    private boolean running = false;
    private int points = 0;

    private BufferStrategy bs;
    private Graphics g;

    private State gameState;
    private State menuState;

    private KeyManager keyManager;

    private GameCamera gameCamera;

    private Handler handler;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.keyManager = new KeyManager();
    }

    public void init() {
        display = new Display(this.title, this.width, this.height);
        display.getFrame().addKeyListener(this.keyManager);
        Asset.init();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);

        gameState = new GameState(handler);
        State.setState(gameState);


        //menuState = new MenuState();
        //State.setState(menuState);

    }

    public void tick() {
        keyManager.tick();

        if (State.getState() != null) {

            State.getState().tick();

        }
    }

    public void render() {
        int i = 0;
        bs = display.getCavnas().getBufferStrategy();
        if (bs == null) {
            display.getCavnas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        if (State.getState() != null) {

            State.getState().render(g);

        }

        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                delta--;
            }

        }
    }

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    public GameCamera getGameCamera() {
        return this.gameCamera;
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate()
    {
        StringBuilder s = new StringBuilder("You died. \n Score: " + points);
        showMessageDialog(null, s);


        insertData(points);
        System.exit(1);
        stop();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addPoints(int x)
    {
        this.points += x;
    }

    public void insertData(int points)
    {
        Database data = new Database();
        data.insertScore(points);
    }
}
