package game;

import java.awt.Point;
import java.awt.Color;
import java.util.Objects;

import javafx.geometry.Dimension2D;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;

public abstract class Car {

    Point position;
    double velocity;
    Color color;
    EmissionClass exhaustType;
    Dimension2D size;
    ImageView texture;
    Timeline exhaust;


    public Car (Point start, double velocity, Color color, EmissionClass exhaustType,
                Dimension2D size, ImageView texture, Timeline exhaust ) {
        this.position = start;
        this.velocity = velocity;
        this.color = color;
        this.exhaustType = exhaustType;
        this.size = size;
        this.texture = texture;
        this.exhaust = exhaust;
        System.out.println ("Hello");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return exhaustType == car.exhaustType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exhaustType);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Dimension2D getSize() {
        return size;
    }

    public void setSize(Dimension2D size) {
        this.size = size;
    }
}
