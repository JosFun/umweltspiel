package visual;
/* JavaFX imports */
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;

public class EnvGame extends Application {
    private final int FRAME_RATE = 25;
    private final double MAX_WIDTH = 1400;
    private final double MAX_HEIGHT = 1050;

    @Override
    public void start ( Stage primary ) {
        primary.setTitle ( "Umweltplakettenspiel" );

        StackPane layout = new StackPane ( );
        Scene scene = new Scene ( layout, 300, 300 );
        primary.setScene ( scene );
        primary.show ( );
    }

    public static void main ( String [ ] args ) {
        Application.launch ( args );
    }
}
