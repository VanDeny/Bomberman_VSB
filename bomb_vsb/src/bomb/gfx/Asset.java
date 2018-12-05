package bomb.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Asset {

    public static int pWidth = 12, pHeight = 20;
    public static int bWidth = 15, bHeight = 16;

    public static BufferedImage stone, grass, border, playerDown, bomb, enemy, playerDeath, bCenter, bUp, bDown, bLeft, bRight, bUpward, bSide, eDead;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/general.png"));

        playerDown = sheet.crop(1, 1, pWidth, pHeight);
        playerDeath = sheet.crop(112, 3, 14, 18);
        bomb = sheet.crop(84, 22, 16, 14);
        enemy = sheet.crop(0, 39, 14, 16);
        eDead = sheet.crop(115,40,14,16);

        bCenter = sheet.crop(71, 204, 18, 18);
        bUp = sheet.crop(73, 189, 12, 15);
        bDown = sheet.crop(85, 236, 12, 15);
        bLeft = sheet.crop(56, 207, 15, 12);
        bRight = sheet.crop(89, 207, 15, 12);
        bUpward = sheet.crop(56, 222, 18, 12);
        bSide = sheet.crop(89, 225, 12, 18);

        stone = sheet.crop(0, 241, bWidth, bHeight - 4);
        grass = sheet.crop(159, 100, bWidth, bHeight);
        border = sheet.crop(bWidth + 3, 241, bWidth, bHeight - 4);
    }
}
