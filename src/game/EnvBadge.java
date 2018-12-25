package game;

import javafx.geometry.Dimension2D;
import observers.DisplaySizeSubject;

import javafx.geometry.Point2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/* The Environmental Badge*/
public class EnvBadge extends GraphicalObject {
    private static final double NATIVE_BADGE_WIDTH = 25;
    private static final double NATIVE_BADGE_HEIGHT = 25;

    private EmissionClass emission;

    /* Every badge has a specific text on it.*/
    private String text;
    public EnvBadge (Point2D position, DisplaySizeSubject displaySize ) {
        super ( position, NATIVE_BADGE_WIDTH, NATIVE_BADGE_HEIGHT, displaySize );

    }

    @Override
    public void determineTexture ( ) {
        if (this.emission == null) {
            double rand = Math.random();
            if (rand < 0.33) {
                this.emission = EmissionClass.EMISSION_CLASS_RED;
            }
            else if (rand < 0.67) {
                this.emission = EmissionClass.EMISSION_CLASS_YELLOW;
            }
            else {
                this.emission = EmissionClass.EMISSION_CLASS_GREEN;
            }
        }

            switch (this.emission) {
                case EMISSION_CLASS_GREEN:
                    this.setTexture(Textures.TEXTURE_GREEN_BADGE);
                    break;
                case EMISSION_CLASS_YELLOW:
                    this.setTexture(Textures.TEXTURE_YELLOW_BADGE);
                    break;
                case EMISSION_CLASS_RED:
                    this.setTexture(Textures.TEXTURE_RED_BADGE);
                    break;
            }
        }

    /* Once you have clicked on the badge, its colour will change. */
    public void onClick ( ) {
        if ( this.emission == EmissionClass.EMISSION_CLASS_RED ) {
            this.emission = EmissionClass.EMISSION_CLASS_YELLOW;
            this.text = "EURO 2";
        }
        else if ( this.emission == EmissionClass.EMISSION_CLASS_YELLOW ) {
            this.emission = EmissionClass.EMISSION_CLASS_GREEN;
            this.text = "EURO 3";
        }
        else if ( this.emission == EmissionClass.EMISSION_CLASS_GREEN) {
            this.text = "EURO 4";
            this.emission = EmissionClass.EMISSION_CLASS_RED;
        }
        /* Last, adapt the badge's colour to the changed emission class*/
        this.determineTexture();
    }

    public EmissionClass getEmission ( ) {
        return ( this.emission );
    }


}
