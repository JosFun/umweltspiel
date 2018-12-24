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
    private static final double NATIVE_WIDTH = 800;
    private static final double NATIVE_HEIGHT = 600;
    private final int FRAME_RATE = 25;
    private final double MAX_WIDTH = 1400;
    private final double MAX_HEIGHT = 1050;
    /* The current score */
    private Score score;
    private ArrayList<Life> lifes = new ArrayList<> ( );
    /* The current initialization velocity.
    * As the game goes on and on, this value will increase. */
    private double currentInitV = 10;
    /* The current time interval, in which cars are craeted.
    * As the game goes on and on, this will value will increase. */
    private double currentCarSpawnInterval = 3;

    /* The native yPositions on which cars can be created. */
    private double [ ] ySlots;
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
        this.currentScene = new Scene ( root,NATIVE_WIDTH, NATIVE_HEIGHT );
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
        /* Determine slots ( ypositions ) on which graphical objects can be created:*/
        this.determineSlots ( );
        /* Create the gameLoop and visualize the application afterwards. */
        this.gameLoop = new EnvGame.Timer ( System.nanoTime());
        this.gameLoop.start ( );
        primary.show ( );
    }

    /* End the game */
    private void endGame ( ) {
        if ( GameOver.showGameOver() == false ) {
            Platform.exit ( );
        }
    }
    /* Redraw all the objects on the screen.
    * Invoked because the width property or the height property of the scene has changed. */
    public void redrawObjects ( ) {
        /* Prime the canvas first */
        this.gameField.primeCanvas ( );
        /* Afterwards draw all the cars */
        for ( Car c: this.cars ) {
            this.gameField.drawObject ( c.getEngine ( ), c, c.getBadge( ) );
        }
        /* Then draw all the trees in the game. */
        for ( Tree t: this.trees ) {
            this.gameField.drawObject( t );
        }
        /* Don't forget the lifes of the player */
        for ( Life l: this.lifes ) {
            this.gameField.drawObject( l );
        }

        /* Last draw the current score */
        this.gameField.drawScore( this.score );
    }

    /* Handle MouseEveent*/
   @Override
   public void handle( MouseEvent m ){
        /* Get origin of the mouseclick */
        double x = m.getSceneX ( );
        double y = m.getSceneY ( );

        for ( Car c: this.cars ) {
            EnvBadge b = c.getBadge();
            Point2D badgeCenter = new Point2D ( b.getPosition ( ).getX ( ) + 0.5 * b.getWidth(),
                                                b.getPosition ( ).getY ( ) + 0.5 * b.getHeight ( ) );
            if ( badgeCenter.distance ( new Point2D ( x, y )) <= b.getWidth() ) {
                b.onClick ( );
                this.redrawObjects();
            }
        }
   }

   public void determineSlots ( ) {
       /* Just create a dummy object so we can calculate the current offset for the trees on the screen. */
       double offset = new Tree ( new Point2D ( 0, 0 ), this.sceneSize ).getForcedDistance();
       int slots = ( int ) ( this.currentScene.getHeight (  )/ offset );
       this.ySlots = new double [ slots ];
       for ( int i = 0; i < slots; ++i ) {
           this.ySlots [ i ] = i * offset;
       }
   }

   private void initLifes ( ) {
       for ( int i = 0; i < 3; ++i ) {
           this.lifes.add(new Life(new Point2D(NATIVE_WIDTH * ( 0.95 - 0.05 * i ), NATIVE_HEIGHT * 0.05), this.sceneSize));
       }
   }
    public void createTrees ( int amount ) {
        /* For each of the tress you want to create: Determine a slot ( yPos ) and a completely random xPos. */
        for ( int i = 0; i < amount;  ) {
            double offset = this.ySlots [ 0 ];
            int slotNum = ( int ) ( this.ySlots.length * Math.random ( ) );
            double xCoord =  ( this.currentScene.getWidth ( ) - offset ) * Math.random ( );
            Point2D rand = new Point2D ( xCoord, this.ySlots [ slotNum ]);


            /* Create new tree until it is far enough form all the other trees having been created so far. */
            boolean enoughDistance = true;
            for ( int j = 0; j <this.trees.size ( ) && enoughDistance; ++j ) {
                Tree t = this.trees.get ( j );
                if ( rand.distance ( t.getPosition() ) < t.getForcedDistance() ) {
                    enoughDistance = false;
                }
            }

            if ( enoughDistance ) {
                Tree t = new Tree(rand, this.sceneSize);
                this.trees.add(t);
                ++i;
            }
        }
    }

    private void createCar ( ) {
        double yRand = this.ySlots [ ( int ) ( this.ySlots.length * Math.random ( ) ) ];
        /* Determine new random y coords if two cars interfere. */
        boolean enoughDistance = true;


        if ( !enoughDistance ) {
            /* Recursion */
            this.createCar ( );
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
                this.score.incScore( 20 );
            }
            /* Otherwise he will lose a life!*/
            else {
                this.lifes.remove ( this.lifes.size ( ) - 1 );
            }
        }
    }

    /* Private inner class to create a timeline.
     * The handle method is invoked by the framework 60 times per second.  */
    private class Timer extends AnimationTimer {
        private static final double interval = 16.67*10e-3;
        private long startTime;

        /* Initialization of the game components. */
        Timer ( long nanoTimeStart ) {
            super ( );
            this.startTime = nanoTimeStart;
            createTrees( 20 );
            createCar ( );
            initLifes();
            score = new Score ( 0, sceneSize ); /* Create score */
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

            if ( lifes.isEmpty() ) {
                endGame ( );
            }
            if ( t >= currentCarSpawnInterval ) {
                /* Flip a coin on whether or not to create a car if enough time has passed since the last creation. */
                if ( Math.random ( ) > 0.5 ) {
                    createCar();
                    /* Reset startTime afterwards. */
                    this.startTime = currentNanoTime;
                    currentCarSpawnInterval -= 0.01;
                }
            }

            redrawObjects();
        }
    }

    public static void main ( String [ ] args ) {
        Application.launch ( args );
    }
}
