package visual;
/* JavaFX imports */
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.Point2D;

/* imports for the ArrayList */
import java.util.ArrayList;
import game.*;

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


    @Override
    public void start ( Stage primary ) {
        primary.setTitle ( "Umweltplakettenspiel" );

        StackPane layout = new StackPane ( );
        this.currentScene = new Scene ( layout, 800, 600 );
        primary.setScene ( this.currentScene );
        primary.show ( );
    }

    public void updateScreen ( ) {
    }

    public void createTrees ( int amount ) {
        for ( int i = 0; i < amount; ) {
           Point2D rand = new Point2D ( this.currentScene.getWidth ( ) * Math.random ( ) ,
                                        this.currentScene.getHeight ( ) * Math.random ( ) );
           boolean enoughDistance = true;
           for ( int j = 0; j < this.trees.size() && enoughDistance; ++j ) {
               Tree t = this.trees.get ( j );
               if ( rand.distance ( t.getPosition() ) < t.getForcedDistance() ) {
                   enoughDistance = false;
               }
           }
           if ( enoughDistance ) ++i;
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
