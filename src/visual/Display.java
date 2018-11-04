package visual;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;

public class Display extends Application {

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
