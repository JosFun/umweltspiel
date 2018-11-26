package game;

import observers.DisplaySizeSubject;

import java.awt.*;

/* The Environmental Badge*/
public class EnvBadge extends GraphicalObject implements Textures{
    EmissionClass emission;

    /* Every badge has a specific text on it.*/
    private String text;
    public EnvBadge (Point position, double width, double height, DisplaySizeSubject displaySize, EmissionClass emission ) {
        super ( position, width, height, displaySize );
        this.emission = emission;
        switch ( this.emission ) {
            case EMISSION_CLASS_BLUE:
                this.setTexture( Textures.TEXTURE_BLUE_BADGE );
                break;
            case EMISSION_CLASS_GREEN:
                this.setTexture( Textures.TEXTURE_GREEN_BADGE );
                break;
            case EMISSION_CLASS_YELLOW:
                this.setTexture( Textures.TEXTURE_YELLOW_BADGE );
                break;
            case EMISSION_CLASS_RED:
                this.setTexture( Textures.TEXTURE_RED_BADGE );
                break;
            case EMISSION_CLASS_PURPLE:
                this.setTexture ( Textures.TEXTURE_PURPLE_BADGE );
        }
    }

    /* Once you have clicked on the badge, its colour will change. */
    public void onClick ( ) {

    }
}
