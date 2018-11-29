package game;

import observers.DisplaySizeObserver;
import observers.DisplaySizeSubject;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import java.awt.Point;

public abstract class GraphicalObject extends DisplaySizeObserver {
    private static final double NATIVE_SCREEN_WIDTH = 800;
    private static final double NATIVE_SCREEN_HEIGHT = 600;

    private String imagePath;
    private Image texture;
    /* The current size of the window, the object is currently being displayed on. */
    private DisplaySizeSubject displaySize;

    Point position;
    Dimension2D size;

    public GraphicalObject (Point position, double width, double height, DisplaySizeSubject displaySize ) {
        this.position = position;
        this.size = new Dimension2D ( width, height );
        /* DisplaySizeSubject shall notify this observer whenever the displaysize changes.*/
        this.displaySize = displaySize;

        /* Use the update method to initialize the width and height of this object by paying respect to the current
        * display size. */
        this.update ( );
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getWidth() {
        return ( this.size.getWidth() );
    }

    public double getHeight() {
        return ( this.size.getHeight ( ) );
    }

    public Dimension2D getSize ( ) { return( this.size ); }

    public void setSize ( double width, double height ) {
        this.size = new Dimension2D ( width, height );
    }

    /* Change the image of this graphical object to the texture specified in the path. */
    public void setTexture ( String path ) {
        this.imagePath = path;
        this.texture = new Image ( this.imagePath, this.getWidth ( ), this.getHeight ( ), false, false );
    }

    /* Implement the update method for the observer:
     *      Whenever the displaySize attribute is changed, scale this graphical object */
    @Override
    public void update ( ) {
        double newWidth = this.getWidth() * ( this.displaySize.getWidth( ) / NATIVE_SCREEN_WIDTH );
        double newHeight = this.getHeight ( ) * ( this.displaySize.getHeight ( ) / NATIVE_SCREEN_HEIGHT );
        this.size = new Dimension2D ( newWidth, newHeight );
        this.texture = new Image ( imagePath, newWidth, newHeight, false, false );
    }


}
