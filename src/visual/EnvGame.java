package visual;
/* JavaFX imports */
import javafx.application.Platform;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.Point2D;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;

/* imports for the ArrayList */
import java.util.ArrayList;
import game.*;
import observers.*;

public class EnvGame extends Application implements EventHandler<MouseEvent> {
    private final int FRAME_RATE = 25;
    private final double MAX_WIDTH = 1400;
    private final double MAX_HEIGHT = 1050;
    /* The current score */
    private int score = 0;
    private int lifes = 0;
    /* The current initialization velocity.
    * As the game goes on and on, this value will increase. */
    private double currentInitV = 10;
    /* The current time interval, in which cars are craeted.
    * As the game goes on and on, this will value will increase. */
    private double currentCarSpawnInterval = 3;

    /* The cars in the game */
    private ArrayList<Car> cars = new ArrayList<> ( );
    /* The trees in the game */
    private ArrayList<Tree> trees = new ArrayList<> ( );

    /* Anything related to the grapical representation of the game */
    private Scene currentScene;
    private Group root;
    private Field gameField;

    private DisplaySizeSubject sceneSize;

    /* This game's gameLoop. */
    private Timer gameLoop;


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
        this.gameField.setOnMouseClicked( this );

        /* Add listeners to width and height property of the game. */
        this.currentScene.widthProperty().addListener( obs -> {
            this.sceneSize.setWidth( this.currentScene.getWidth ( ));
            this.sceneSize.execute(); /* Notify all observers */
            this.gameField.updateSize ( ); /* Update the size of the gameField. */
            this.redrawObjects ( ); /* Redraw all the objects on the screen. */
        });
        this.currentScene.heightProperty().addListener ( obs -> {
            this.sceneSize.setHeight ( this.currentScene.getHeight () );
            this.sceneSize.execute ();
            this.gameField.updateSize ( );
            this.redrawObjects ( );
        });
        /* Create the gameLoop and visualize the application afterwards. */
        this.gameLoop = new EnvGame.Timer ( System.nanoTime());
        this.gameLoop.start ( );
        primary.show ( );
    }

    /* Redraw all the objects on the screen.
    * Invoked because the width property or the height property of the scene has changed. */
    public void redrawObjects ( ) {
        this.gameField.primeCanvas ( );
        for ( Car c: this.cars ) {
            this.gameField.drawObject ( c.getEngine ( ), c, c.getBadge( ) );
        }

        for ( Tree t: this.trees ) {
            this.gameField.drawObject( t );
        }
    }

   @Override
   public void handle( MouseEvent m ){
        /* Get origin of the mouseclick */
        double x = m.getSceneX ( );
        double y = m.getSceneY ( );

        for ( Car c: this.cars ) {
            EnvBadge b = c.getBadge();
            Point2D badgeCenter = new Point2D ( b.getPosition ( ).getX ( ) + 0.5 * b.getWidth(),
                                                b.getPosition ( ).getY ( ) + 0.5 * b.getHeight ( ) );
            if ( badgeCenter.distance ( new Point2D ( x, y )) <= 0.75 * b.getWidth() ) {
                b.onClick ( );
                this.redrawObjects();
            }
        }
   }

    public void createTrees ( int amount ) {
        /* Just create a dummy object so we can calculate the current offset for the trees on the screen. */
        double offset = new Tree ( new Point2D ( 0, 0 ), this.sceneSize ).getForcedDistance();

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
        double offset = new Euro2Car (0, 0, this.sceneSize ).getHeight();
        double yRand = ( this.currentScene.getHeight ( ) - 2 * offset ) * Math.random ( );
        /* Determine new random y coords if two cars interfere. */
        boolean enoughDistance = true;
        for ( int i = 0; i < this.cars.size() && enoughDistance; ++i ) {
            if ( Math.abs ( yRand - this.cars.get ( i ).getPosition ( ).getY() ) < 1.25 * offset ) {
                enoughDistance = false;
            }
        }

        if ( !enoughDistance ) {
            /* Recursion */
            createCar ( );
            return;
        }
        /* Coordinates have been created randomly successfully.
        * Now we have to determine the car's emission class. */
        double rand = Math.random ( );
        Car car;
        if ( rand < 0.33 ) {
            car = new Euro2Car( yRand, this.currentInitV, this.sceneSize );
        }
        else if ( rand < 0.67) {
            car = new Euro3Car ( yRand, this.currentInitV, this.sceneSize );
        }
        else {
            car = new Euro4Car ( yRand, this.currentInitV, this.sceneSize );
        }

        this.cars.add ( car );

    }

    /* Evaluate a car and calculate score and lifes according to the evaluation properly. */
    private void evaluate ( Car c ) {
        if (!gameField.isOnField( c )) {
            /* Remove the car from our list. */
            cars.remove ( c );
            /* If badge has been set correctly: Player receives some points!*/
            if ( c.checkBadge() ) {
                score += 20;
            }
            /* Otherwise he will lose a life!*/
            else {
                lifes -= 1;
            }
        }
    }

    /* Private inner class to create a timeline.
     * The handle method is invoked by the framework 60 times per second.  */
    private class Timer extends AnimationTimer {
        private static final double interval = 16.67*10e-3;
        private long startTime;

        Timer ( long nanoTimeStart ) {
            super ( );
            this.startTime = nanoTimeStart;
            createTrees( 7 );
            createCar ( );
            redrawObjects ( );
        }

        @Override
        public void handle ( long currentNanoTime ) {
            double t = ( currentNanoTime - this.startTime ) / 10e8;

            for ( int i = 0; i < cars.size ( ); ++i ) {
                Car c = cars.get ( i );
                c.updatePos( interval );
                /* If the car has just left the field: Evaluate it. */
                evaluate ( c );
            }
            System.out.println ( t );
            if ( t >= currentCarSpawnInterval ) {
                /* Flip a coin on whether or not to create a car if enough time has passed since the last creation. */
                if ( Math.random ( ) > 0.5 ) {
                    createCar();
                    /* Reset startTime afterwards. */
                    this.startTime = currentNanoTime;
                    currentCarSpawnInterval -= 0.05;
                }
            }

            redrawObjects();
        }
    }

    public static void main ( String [ ] args ) {
        Application.launch ( args );
    }
}
