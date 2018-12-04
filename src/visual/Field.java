package visual;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import observers.DisplaySizeSubject;
import game.Textures;

public class Field extends Canvas {
    private DisplaySizeSubject sceneSize;

    public Field (DisplaySizeSubject sceneSize ) {
        this.sceneSize = sceneSize;
        this.updateSize ( );
    }

    public void updateSize ( ) {
        this.setHeight ( this.sceneSize.getHeight() );
        this.setWidth ( this.sceneSize.getWidth () );
        Image background = new Image ( Textures.BACKGROUND_FIELD, this.getWidth(), this.getHeight(), false, false );
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.drawImage( background, 0, 0, this.getWidth(), this.getHeight ( ));
    }
}
