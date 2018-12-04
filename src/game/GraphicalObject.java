package game;

import observers.DisplaySizeObserver;
import observers.DisplaySizeSubject;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.geometry.*;

public abstract class GraphicalObject extends DisplaySizeObserver implements Textures {
    private static final double NATIVE_SCREEN_WIDTH = 800;
    private static final double NATIVE_SCREEN_HEIGHT = 600;

    private String imagePath;
    private Image texture;
    /* The current size of the window, the object is currently being displayed on. */
    private DisplaySizeSubject displaySize;

    /* The current position and size of the object ( relatively to the current displaySize */
    Point2D position;
    Dimension2D size;

    /* The position and size the object has been created with
    *  -> Used in order to scale an objects position and size according to the current screen size
    *     These fields are being used in the update method of a GraphicalObject*/
    private Point2D nativePosition;
    private Dimension2D nativeSize;

    /* The distance in which two graphical objects have to be created in */
    double forcedDistance;


    public GraphicalObject (Point2D position, double width, double height, DisplaySizeSubject displaySize) {
        /* Set native positions */
        this.nativePosition = position;
        this.nativeSize = new Dimension2D ( width, height );

        /* Set current position */
        this.position = position;
        this.size = new Dimension2D ( width, height );
        /* DisplaySizeSubject shall notify this observer whenever the displaysize changes.*/
        this.displaySize = displaySize;
        /* Add this graphical object to the displaySizes list of observers. */
        this.displaySize.add ( this );
        /* use pythagoras for forced distance */
        this.forcedDistance = Math.sqrt ( Math.pow ( width, 2 ) + Math.pow ( height, 2 ));
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
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

    public double getForcedDistance ( ) { return ( this.forcedDistance );}

    public Image getTexture ( ) { return ( this.texture ); }

    /* Change the image of this graphical object to the texture specified in the path. */
    public void setTexture ( String path ) {
        this.imagePath = path;
        this.texture = new Image ( path, this.getWidth(), this.getHeight ( ), false, false  );
        /* Use the update method to initialize the width and height of this object by paying respect to the current
         * display size. */
        this.update ( );
    }

    /* Implement the update method for the observer:
     *      Whenever the displaySize attribute is changed, scale this graphical object */
    @Override
    public void update ( ) {
        /* Update size */
        double newWidth = this.nativeSize.getWidth() * ( this.displaySize.getWidth( ) / NATIVE_SCREEN_WIDTH );
        double newHeight = this.nativeSize.getHeight() * ( this.displaySize.getHeight ( ) / NATIVE_SCREEN_HEIGHT );
        this.size = new Dimension2D ( newWidth, newHeight );
        this.texture = new Image ( imagePath, newWidth, newHeight, false, false );
        /* Update coordinates */
        double newXPos = this.nativePosition.getX() * ( this.displaySize.getWidth ( ) / NATIVE_SCREEN_WIDTH );
        double newYPos = this.nativePosition.getY() * ( this.displaySize.getHeight ( ) / NATIVE_SCREEN_HEIGHT );
        this.position = new Point2D ( newXPos, newYPos );
        /* use pythagoras for forced distance */
        this.forcedDistance = Math.sqrt ( Math.pow ( this.getWidth(), 2 ) + Math.pow ( this.getHeight(), 2 ));
    }


}
