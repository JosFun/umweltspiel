package game;

import javafx.geometry.Point2D;

import observers.DisplaySizeSubject;

public class Euro2Car extends Car{
    public Euro2Car ( double yCoord , double velocity, DisplaySizeSubject displaySize) {
        super ( new Point2D ( 30, yCoord ), velocity, EmissionClass.EMISSION_CLASS_RED, displaySize );
        this.determineTexture();
    }
}
