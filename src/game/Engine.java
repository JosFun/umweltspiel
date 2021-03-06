package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

public class Engine extends GraphicalObject {
    private static final double NATIVE_EXHAUST_WIDTH = 30;
    private static final double NATIVE_EXHAUST_HEIGHT = 15;

    final double MAX_VELOCITY = 130;

    private EmissionClass emission = EmissionClass.EMISSION_CLASS_GREEN; /* This engine's EmissionClass. */
    private double nativeVelocity; /* The native velocity of this engine when the screen is of native size. */
    private double velocity; /* The actual velocity of this engine on the scaled screen. */

    public Engine (Point2D initPos, double v, EmissionClass e, DisplaySizeSubject displaySize) {
        super(initPos, NATIVE_EXHAUST_WIDTH, NATIVE_EXHAUST_HEIGHT, displaySize);
        this.nativeVelocity = v;
        this.emission = e;
        this.determineTexture();
        this.update ( );
    }

    /* Whenever the displaySizeSubject changes: Invoke the super method of the GraphicalObject type and
    *  adapt velocity as well. */
    @Override
    public void update ( ){
        super.update ( );
        this.velocity = this.nativeVelocity * ( this.displaySize.getWidth( ) / GraphicalObject.NATIVE_SCREEN_WIDTH );

    }

    public EmissionClass getEmission() {
        return this.emission;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getNativeVelocity ( ) {
        return ( this.nativeVelocity );
    }
    public void setNativeVelocity ( double v ) {
        this.velocity = v;
    }

    /* Determine the texture of this engine according to its emissionclass. */
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
