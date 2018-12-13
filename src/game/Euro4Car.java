package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

public class Euro4Car extends Car {
    public Euro4Car ( double yCoord, double velocity, DisplaySizeSubject displaySize ) {
        super ( new Point2D ( 30, yCoord ), velocity, EmissionClass.EMISSION_CLASS_GREEN, displaySize );
        this.determineTexture();
    }
}
