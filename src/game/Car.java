package game;

import observers.DisplaySizeSubject;

import java.awt.Point;
import java.awt.Color;
import java.util.Objects;
import javafx.geometry.Dimension2D;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;

public abstract class Car extends GraphicalObject {
    double velocity;
    Color color;
    EmissionClass exhaustType;
    Timeline exhaust;

    public Car (Point start, double velocity, Color color, EmissionClass exhaustType,
                double width, double height, DisplaySizeSubject displaySize,
                String imagePath, Timeline exhaust ) {
        super ( start, 200, 300, displaySize );
        this.setTexture( imagePath );
        this.velocity = velocity;
        this.color = color;
        this.exhaustType = exhaustType;
        this.exhaust = exhaust;
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
    /* Getters and Setters */
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
}
