package edu.sdu.woz.util;

import edu.sdu.woz.Game;
import edu.sdu.woz.IFacade;
import edu.sdu.woz.room.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RoomMapper implements IFacade {

    public static void main(String[] args) {
        new RoomMapper();
    }

    private RoomMapper() {
        Game game = new Game(this);
        int roomSize = 100;
        int offset = 5;
        BufferedImage img = new BufferedImage(12 * roomSize, 12 * roomSize, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img.createGraphics();

        for (Room r : game.getMap().values()) {
            Point pos = r.getPosition();
            pos = new Point(pos.x + offset, - pos.y + offset);

            graphics.drawRect(pos.x * roomSize, pos.y * roomSize, roomSize - 1, roomSize - 1);
            graphics.drawString(r.getClass().getSimpleName(), pos.x*roomSize + roomSize/4, pos.y*roomSize+roomSize/2);
        }
        try {
            ImageIO.write(img, "PNG", new File("/tmp/rooms.png"));
            Runtime.getRuntime().exec("feh -Z /tmp/rooms.png").waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public void onRoomEnter(Room room) {
    }
}
