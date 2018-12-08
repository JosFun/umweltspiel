package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

public class Engine extends GraphicalObject {
    private static final double NATIVE_EXHAUST_WIDTH = 20;
    private static final double NATIVE_EXHAUST_HEIGHT = 10;

    final double MAX_VELOCITY = 130;

    private EmissionClass emission; /* This engine's EmissionClass. */
    private double velocity; /* The current velocity of this engine. */

    public Engine (Point2D initPos, double v, EmissionClass e, DisplaySizeSubject displaySize) {
        super(initPos, NATIVE_EXHAUST_WIDTH, NATIVE_EXHAUST_HEIGHT, displaySize);
        this.velocity = v;
        this.emission = e;
    }

    public EmissionClass getEmission() {
        return emission;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity ( double v ) {
        this.velocity = v;
    }

    @Override
    public void determineTexture ( ) {
        if (this.emission == EmissionClass.EMISSION_CLASS_RED) {
            this.setTexture ( TEXTURE_TOXIC_EXHAUST );
        } else if (this.emission == EmissionClass.EMISSION_CLASS_YELLOW) {
            this.setTexture ( TEXTURE_DIRTY_EXHAUST );
        } else if (this.emission == EmissionClass.EMISSION_CLASS_GREEN) {
            this.setTexture ( TEXTURE_CLEAN_EXHAUST );
        }
    }
}
