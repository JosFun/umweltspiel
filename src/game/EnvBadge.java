package game;

import observers.DisplaySizeSubject;

import java.awt.*;

/* The Environmental Badge*/
public class EnvBadge extends GraphicalObject implements Textures{
    private static final double NATIVE_BADGE_WIDTH = 100;
    private static final double NATIVE_BADGE_HEIGHT = 50;

    private EmissionClass emission;

    /* Every badge has a specific text on it.*/
    private String text;
    public EnvBadge (Point position, DisplaySizeSubject displaySize, EmissionClass emission ) {
        super ( position, NATIVE_BADGE_WIDTH, NATIVE_BADGE_HEIGHT, displaySize );
        this.emission = emission;
    }

    private void adaptTexture ( ) {
        switch ( this.emission ) {
            case EMISSION_CLASS_GREEN:
                this.setTexture( Textures.TEXTURE_GREEN_BADGE );
                break;
            case EMISSION_CLASS_YELLOW:
                this.setTexture( Textures.TEXTURE_YELLOW_BADGE );
                break;
            case EMISSION_CLASS_RED:
                this.setTexture( Textures.TEXTURE_RED_BADGE );
        }
    }

    /* Once you have clicked on the badge, its colour will change. */
    private void onClick ( ) {
        if ( this.emission == EmissionClass.EMISSION_CLASS_RED ) {
            this.emission = EmissionClass.EMISSION_CLASS_YELLOW;
            this.text = "EURO 2";
        }
        else if ( this.emission == EmissionClass.EMISSION_CLASS_YELLOW ) {
            this.emission = EmissionClass.EMISSION_CLASS_GREEN;
            this.text = "EURO 3";
        }
        else {
            this.text = "EURO 4";
            this.emission = EmissionClass.EMISSION_CLASS_RED;
        }
        /* Last, adapt the badge's colour to the changed emission class*/
        this.adaptTexture();
    }

    public EmissionClass getEmission ( ) {
        return ( this.emission );
    }
}
