package game;
import observers.DisplaySizeSubject;
import javafx.geometry.Point2D;

/* This class represents a life of the player. */
public class Life extends GraphicalObject {
    private static final double NATIVE_WIDTH = 40;
    private static final double NATIVE_HEIGHT = 40;

    public Life ( Point2D pos, DisplaySizeSubject size ) {
        super ( pos, NATIVE_WIDTH, NATIVE_HEIGHT, size );
        this.determineTexture();
    }

    /* determine this object's texture */
    @Override
    public void determineTexture ( ) {
        this.setTexture ( Textures.TEXTURE_LIFE );
    }

}
