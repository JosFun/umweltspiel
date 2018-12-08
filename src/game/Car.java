package game;

import observers.DisplaySizeSubject;

import java.awt.Color;
import java.util.Objects;
import javafx.geometry.Dimension2D;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public abstract class Car extends GraphicalObject{
    /* The native car width and height on a display sized 800 times 600 pixels. */
    private static final double NATIVE_CAR_WIDTH = 100;
    private static final double NATIVE_CAR_HEIGHT = 50;
    private final double MAX_VELOCITY = 130;

    private Timeline exhaust;
    private Engine eng;
    private EnvBadge badge;

    public Car (Point2D start, double velocity, EmissionClass exhaustType,
                DisplaySizeSubject displaySize ) {
        super ( start, NATIVE_CAR_WIDTH, NATIVE_CAR_HEIGHT, displaySize );

        /* Create this car's engine based on its emission class and its velocity. */
        this.eng = new Engine ( new Point2D ( 0, this.getPosition ( ).getY ( )), velocity, exhaustType, displaySize );

        /* Create this car's badge and determine its emission class randomly. */
       double rand = Math.random ( );
        if ( rand < 0.33 ) {
            this.badge = new EnvBadge ( start, displaySize, EmissionClass.EMISSION_CLASS_GREEN );
        }
        else if ( rand < 0.67 ) {
            this.badge = new EnvBadge ( start, displaySize, EmissionClass.EMISSION_CLASS_YELLOW );
        }
        else {
            this.badge = new EnvBadge ( start, displaySize, EmissionClass.EMISSION_CLASS_RED );
        }
    }

    @Override
    public void determineTexture ( ) {
        double rand = Math.random ( );
        if ( rand < 0.33 ) {
            this.setTexture( TEXTURE_CAR_BROWN );
        }
        else if ( rand < 0.67 ) {
            this.setTexture ( TEXTURE_CAR_GREY );
        }
        else {
            this.setTexture( TEXTURE_CAR_WHITE );
        }
    }

    public void updatePos ( double timeInterval) {
        double veloc = this.eng.getVelocity();
        this.setPosition ( new Point2D ( this.getPosition().getX ( ) + timeInterval * veloc,
                           this.getPosition ( ).getY ( ) ) );
        this.eng.setPosition( new Point2D ( this.eng.getPosition().getX ( ) + timeInterval * veloc,
                              this.getPosition ( ).getY ( ) ) );
        this.badge.setPosition ( new Point2D ( this.eng.getPosition ( ).getX ( ) + timeInterval * veloc,
                                this.badge.getPosition ( ).getY ( )) );
    }
    /* If this car's engine's emission class equals this car's badge's emissionclass: return true.
    *  Otherwise: The badge has been chosen wrongly. */
    public boolean checkBadge ( ) {
        return ( this.eng.getEmission() == this.badge.getEmission());
    }

    public EnvBadge getBadge ( ) { return ( this.badge ); }
    public Engine getEngine ( ) { return ( this.eng ); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return ( this.eng == car.eng );
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.eng );
    }
}
