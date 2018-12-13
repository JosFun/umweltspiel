package visual;

import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import observers.DisplaySizeSubject;
import game.*;

/* The Field of the game on which all the cars and trees are drawn. */
public class Field extends Canvas {
    private DisplaySizeSubject sceneSize;
    private Image background;

    public Field (DisplaySizeSubject sceneSize ) {
        this.sceneSize = sceneSize;
        this.updateSize ( );
    }
    /* Update the size of this field according to the size of the screen it has been created on. */
    public void updateSize ( ) {
        this.setHeight ( this.sceneSize.getHeight() );
        this.setWidth ( this.sceneSize.getWidth () );
        this.background = new Image ( Textures.BACKGROUND_FIELD, this.getWidth(), this.getHeight(), false, false );
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.drawImage( this.background, 0, 0, this.getWidth(), this.getHeight ( ));
    }

    /* Draw the objects specified by g [ ]*/
    public void drawObject ( GraphicalObject... g ) {
        GraphicsContext gc = this.getGraphicsContext2D();
        for ( GraphicalObject obj: g ) {
            gc.drawImage(obj.getTexture(), obj.getPosition().getX(), obj.getPosition().getY());
        }
    }

    /* Prime the canvas with its background. */
    public void primeCanvas ( ) {
        this.getGraphicsContext2D().drawImage( this.background, 0, 0, this.getWidth(), this.getHeight ( ));
    }

    /* Checks whether or not the GraphicalObject g is still on the field. */
    public boolean isOnField ( GraphicalObject g ) {
        double x = g.getPosition().getX();
        double y = g.getPosition().getY( );
        return ( x + g.getWidth() < this.getWidth() && y + g.getHeight ( ) < this.getHeight ( ) );
    }
}
