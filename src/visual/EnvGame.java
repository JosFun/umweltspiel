package visual;
/* JavaFX imports */
import javafx.application.Platform;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.Point2D;

/* imports for the ArrayList */
import java.util.ArrayList;
import game.*;
import observers.*;

public class EnvGame extends Application {
    private final int FRAME_RATE = 25;
    private final double MAX_WIDTH = 1400;
    private final double MAX_HEIGHT = 1050;
    /* The current score */
    private int score = 0;

    /* The cars in the game */
    private ArrayList<Car> cars = new ArrayList<> ( );
    /* The trees in the game */
    private ArrayList<Tree> trees = new ArrayList<> ( );

    /* Anything related to the grapical representation of the game */
    private Scene currentScene;
    private Group root;
    private Field gameField;

    private DisplaySizeSubject sceneSize;


    @Override
    public void start ( Stage primary ) {
        if ( Menu.displayMenu() == false ) {
            Platform.exit ( );
        }
        primary.setTitle ( "Umweltplakettenspiel" );

        this.root = new Group ( );
        this.currentScene = new Scene ( root, 800, 600 );
        primary.setScene ( this.currentScene );

        /* Create displaySizeSubject which keeps track of the size of this scene */
        this.sceneSize = new DisplaySizeSubject( this.currentScene.getWidth(), this.currentScene.getHeight ( ));

        /* Create the gameField for this game */
        this.gameField = new Field ( this.sceneSize );
        root.getChildren().add ( this.gameField );

        /* Add listeners to width and height property of the game. */
        this.currentScene.widthProperty().addListener( obs -> {
            this.sceneSize.setWidth( this.currentScene.getWidth ( ));
            this.sceneSize.execute(); /* Notify all observers */
            this.gameField.updateSize ( ); /* Update the size of the gameField. */
            this.redrawObjects ( );
        });
        this.currentScene.heightProperty().addListener ( obs -> {
            this.sceneSize.setHeight ( this.currentScene.getHeight () );
            this.sceneSize.execute ();
            this.gameField.updateSize ( );
            this.redrawObjects ( );
        });

        /* Just to test whether the drawing mechanism of the objects even works.
        * The creation of trees and cars will probably be implemented anywhere else later. */
        this.createTrees( 5 );
        primary.show ( );
    }

    /* Redraw all the objects on the screen.
    * Invoked because the width property or the height property of the scene has changed. */
    public void redrawObjects ( ) {
        for ( Tree t: this.trees ) {
            this.gameField.drawObject( t );
        }
    }

    public void updateScreen ( ) {

    }

    public void createTrees ( int amount ) {
        /* Just create a dummy object so we can calculate the current offset for the trees on the screen. */
        double offset = 2 * new Tree ( new Point2D ( 0, 0 ), this.sceneSize ).getForcedDistance();

        for ( int i = 0; i < amount; ) {
            /* Since we do not want to have trees on the edge of the field, we take into account an offset when we
            * calculate the random positions for the trees. */
            Point2D rand = new Point2D ( ( this.currentScene.getWidth ( ) - offset ) * Math.random ( ) + 0.5 * offset ,
                   ( this.currentScene.getHeight ( ) - offset ) * Math.random ( ) + 0.5 * offset );
           boolean enoughDistance = true;
           for ( int j = 0; j < this.trees.size() && enoughDistance; ++j ) {
               Tree t = this.trees.get ( j );
               if ( rand.distance ( t.getPosition() ) < t.getForcedDistance() ) {
                   enoughDistance = false;
               }
           }
           if ( enoughDistance ) {
               Tree t = new Tree ( rand, this.sceneSize );
               this.trees.add ( t );
               ++i;
           }
        }
    }

    public void createCar ( ) {

    }

    public void evaluate ( ) {

    }

    public static void main ( String [ ] args ) {
        Application.launch ( args );
    }
}
