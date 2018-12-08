package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

public class Euro3Car extends Car {
    public Euro3Car (double yCoord, double velocity, DisplaySizeSubject displaySize ) {
        super ( new Point2D ( 20, yCoord ), velocity, EmissionClass.EMISSION_CLASS_YELLOW, displaySize );
        this.determineTexture();
    }
}
