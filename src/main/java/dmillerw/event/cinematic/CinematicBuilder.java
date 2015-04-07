package dmillerw.event.cinematic;

import com.google.common.collect.Lists;

import java.util.LinkedList;

/**
 * @author dmillerw
 */
public class CinematicBuilder {

    public String name;
    public LinkedList<Point> points;
    public int speed = 0;
    public boolean loop = false;


    public CinematicBuilder() {
        this.points = Lists.newLinkedList();
    }

    public CinematicBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CinematicBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public CinematicBuilder loop() {
        this.loop = true;
        return this;
    }

    public CinematicBuilder addPoint(Point point) {
        this.points.add(point);
        return this;
    }

    public Cinematic build() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Cinematic name cannot be null!");
        }

        if (speed <= 0) {
            throw new IllegalStateException("Cinematic speed must be greater than zero!");
        }

        if (points.size() < 2) {
            throw new IllegalStateException("Cinematic must have at least two points!");
        }

        Cinematic cinematic = new Cinematic(points, speed);
        if (loop) {
            cinematic.loop();
        }
        return cinematic;
    }
}
