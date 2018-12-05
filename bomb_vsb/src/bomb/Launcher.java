package bomb;

public class Launcher {

    public static void main(String[] args)
    {
        Game game = new Game("Bomberman", 800,600);
        game.start();
    }
}
